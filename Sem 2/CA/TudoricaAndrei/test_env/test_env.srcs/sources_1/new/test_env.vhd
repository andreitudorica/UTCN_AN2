library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity test_env is
    Port ( clk : in STD_LOGIC;
           btn : in STD_LOGIC_VECTOR (4 downto 0);
           sw : in STD_LOGIC_VECTOR (15 downto 0);
           led : out STD_LOGIC_VECTOR (15 downto 0);
           an : out STD_LOGIC_VECTOR (3 downto 0);
           cat : out STD_LOGIC_VECTOR (6 downto 0));
end test_env;

architecture Behavioral of test_env is
    component  mpg is
        Port ( clk : in STD_LOGIC;btn : in STD_LOGIC;step : out STD_LOGIC);
    end component;
    
    component reg_file is
    port (clk : in std_logic;ra1 : in std_logic_vector (2 downto 0);ra2 : in std_logic_vector (2 downto 0);wa : in std_logic_vector (2 downto 0);wd : in std_logic_vector (15 downto 0);wen : in std_logic; rd1 : out std_logic_vector (15 downto 0);rd2 : out std_logic_vector (15 downto 0); readR3 : out std_logic_vector(15 downto 0));
        end component;
    
    component SSD is
        Port (nr1,nr2,nr3,nr4: in std_logic_vector(3 downto 0); clk: in std_logic;   cat: out std_logic_vector(6 downto 0);  an: out std_logic_vector(3 downto 0));
    end component;
    
    signal PC   : std_logic_vector(15 downto 0):=x"0000";
    type mem is array (0 to 255) of std_logic_vector(15 downto 0);
    signal ram: mem;
    signal rom: mem:=(
        x"0007",
        x"2006",
        x"0497",
        x"0927",
        x"2901",
        x"0db7",
        x"0530",
        x"0497",
        x"0890",
        x"0927",
        x"0d20",
        b"001_000_000_1111111",
        x"1247",
        b"011_100_011_000_1_010",
        x"a076",
others =>x"0000");
    signal en_cnt,cnt_rst,REGDST,ALUSRC,BEQ,BNE,BGEZ,JMP,MEMW,MEMtoREG,REGW:std_logic;
    signal wd,reg_out1,reg_out2,mux_mid,mux_top,plus,j_address,br_address: std_Logic_vector(15 downto 0);
    signal ram_in,ram_out,disp_data,INSTRUCTION:std_logic_vector(15 downto 0);
    signal ALUCTRL:std_logic_vector(2 downto 0);
    signal ALUF1,ALUF2,ALUOUT:std_logic_vector(15 downto 0);
    signal IMMEDIATE:std_logic_vector(15 downto 0);
    signal ALUZERO,SHIFT_AMOUNT, extop, wen: std_logic;
    signal wa: std_Logic_vector(2 downto 0);
    signal read_reg_3: std_Logic_vector(15 downto 0);
begin
    ssd1: SSD Port map( nr1=>disp_data(3 downto 0),    nr2=>disp_data(7 downto 4),    nr3=>disp_data(11 downto 8),    nr4=>disp_data(15 downto 12), clk=>clk,   cat=>cat,   an=>an);
    mpg0: mpg Port map ( clk     => clk,btn     => btn(0), step    => en_cnt);
    mpg3: mpg Port map ( clk     => clk,btn     => btn(3), step    => cnt_rst);
    wen<= REGW and en_cnt;
    rf1: reg_file Port map(clk => clk,ra1 => INSTRUCTION(12 downto 10),ra2 => INSTRUCTION(9 downto 7),   wa => wa ,  wd => wd,  wen => wen,   rd1 => reg_out1,   rd2 => reg_out2, readR3=>read_reg_3);
    process(clk)
    begin
        if(clk'event and clk='1') then
            if en_cnt = '1' then
                PC <= mux_top;
            end if;             
        end if;
        if( cnt_rst='1') then
            PC<=x"0000";
        end if;
    end process; 
    
    --mux for regdst
    process(REGDST)
    begin
        case(REGDST) is
        when '0'=> wa<=INSTRUCTION(9 downto 7);
        when '1'=> wa<=INSTRUCTION(6 downto 4);
        end case;
     end process;
    
    process(BNE,BEQ,BGEZ) 
    begin 
       if ((BEQ and ALUZERO) or (BNE and not ALUZERO) or (not ALUOUT(15) and BGEZ)) = '1' then 
           mux_mid<=IMMEDIATE+PC+1;
       else
          mux_mid<=PC+1;
       end if;
    end process;
    
    process(JMP, j_address, mux_mid) 
    begin 
       if JMP= '1' then
           mux_top<=j_address;
       else 
           mux_top<=mux_mid;
       end if;
    end process;
    
     
    --rom
   INSTRUCTION<=rom(conv_integer(PC(7 downto 0)));
    
    --ram
    process(clk)
    begin
        if(clk'event and clk='1')  then
            if(MEMW='1') then
                ram(conv_integer(ALUOUT))<=reg_out2;  
            end if;
           end if;
        ram_out<=ram(conv_integer(ALUOUT));
    end process;
    
    --memory to register mux
    wd<=ram_out when MEMtoREG='1' else ALUOUT;
    
    --control unit
    process(INSTRUCTION(15 downto 13))
        begin 
        case INSTRUCTION(15 downto 13) is
            when "000" =>  REGDST<='1'; ALUSRC<='0'; BNE<='0'; BEQ<='0'; BGEZ<='0'; JMP<='0'; MEMW<='0'; MEMtoREG<='0'; REGW<='1'; ALUCTRL<= INSTRUCTION(2 downto 0); SHIFT_AMOUNT<=INSTRUCTION(3); IMMEDIATE<="0000000000000000";
            when "001" =>  REGDST<='0'; ALUSRC<='1'; BNE<='0'; BEQ<='0'; BGEZ<='0'; JMP<='0'; MEMW<='0'; MEMtoREG<='0'; REGW<='1'; ALUCTRL<= "000";  IMMEDIATE(6 downto 0)<=INSTRUCTION(6 downto 0); IMMEDIATE(15 downto 7)<=(others=>INSTRUCTION(6));
            when "010" =>  REGDST<='0'; ALUSRC<='1'; BNE<='0'; BEQ<='0'; BGEZ<='0'; JMP<='0'; MEMW<='0'; MEMtoREG<='1'; REGW<='1'; ALUCTRL<= "000"; IMMEDIATE(6 downto 0)<=INSTRUCTION(6 downto 0); IMMEDIATE(15 downto 7)<=(others=>INSTRUCTION(6));
            when "011" =>  REGDST<='0'; ALUSRC<='1'; BNE<='0'; BEQ<='0'; BGEZ<='0'; JMP<='0'; MEMW<='1'; MEMtoREG<='0'; REGW<='0'; ALUCTRL<= "000"; IMMEDIATE(6 downto 0)<=INSTRUCTION(6 downto 0); IMMEDIATE(15 downto 7)<=(others=>INSTRUCTION(6));
            when "100" =>  REGDST<='0'; ALUSRC<='0'; BNE<='0'; BEQ<='1'; BGEZ<='0'; JMP<='0'; MEMW<='0'; MEMtoREG<='0'; REGW<='0'; ALUCTRL<= "001"; IMMEDIATE(6 downto 0)<=INSTRUCTION(6 downto 0); IMMEDIATE(15 downto 7)<=(others=>INSTRUCTION(6));
            when "101" =>  REGDST<='0'; ALUSRC<='1'; BNE<='0'; BEQ<='0'; BGEZ<='1'; JMP<='0'; MEMW<='0'; MEMtoREG<='0'; REGW<='0'; ALUCTRL<= "000"; IMMEDIATE(6 downto 0)<=INSTRUCTION(6 downto 0); IMMEDIATE(15 downto 7)<=(others=>INSTRUCTION(6));
            when "110" =>  REGDST<='0'; ALUSRC<='0'; BNE<='1'; BEQ<='0'; BGEZ<='0'; JMP<='0'; MEMW<='0'; MEMtoREG<='0'; REGW<='0'; ALUCTRL<= "001"; IMMEDIATE(6 downto 0)<=INSTRUCTION(6 downto 0); IMMEDIATE(15 downto 7)<=(others=>INSTRUCTION(6));
            when "111" =>  REGDST<='0'; ALUSRC<='1'; BNE<='0'; BEQ<='0'; BGEZ<='0'; JMP<='1'; MEMW<='0'; MEMtoREG<='0'; REGW<='0'; ALUCTRL<= "000"; j_address<=PC(15 downto 14) & INSTRUCTION(12 downto 0) & '0'; extop<='0';
        end case;
    end process;
    
    --ALU
    ALUF1<=reg_out1;
    process(ALUCTRL,ALUSRC)
        begin
             if(ALUSRC='0') then
                       ALUF2<=reg_out2;
                    else if(BGEZ='1') then
                    ALUF2<=x"0000";
                    else
                       ALUF2<=IMMEDIATE;
                       end if;
              end if;
            case (ALUCTRL) is
                when "000" => ALUOUT <= ALUF1 + ALUF2;--add
                when "001" => ALUOUT <= ALUF1 - ALUF2;--substract
                when "010" => if(SHIFT_AMOUNT='1') then ALUOUT <= ALUF1(14 downto 0)&'0'; else ALUOUT<=ALUF1; end if;--shift left logical
                when "011" => if(SHIFT_AMOUNT='1') then ALUOUT <= '0'& ALUF1(15 downto 1); else ALUOUT<=ALUF1; end if;--shift right logical
                when "100" => ALUOUT <= ALUF1;
                when "101" => ALUOUT <= ALUF1 and ALUF2;
                when "110" => ALUOUT <= ALUF1 or ALUF2;
                when "111" => ALUOUT <= ALUF1 xor ALUF2;
            end case;
            if(ALUOUT="0000000000000000") then
                ALUZERO<='1';
            else 
                ALUZERO<='0';
            end if;
    end process;
    
    
    led<=PC;
    --mux display
    process(sw(3 downto 0))
        begin 
        case sw(3 downto 0) is
            when "0000" => disp_data<=INSTRUCTION;
            when "0001" => disp_data(15 downto 0)<=ALUOUT;
            when "0010" => disp_data(15 downto 0)<=wd;
            when "0011" => disp_data(15 downto 0)<=IMMEDIATE;
            when "0100" => disp_data<=ALUF1;
            --when "0101" => disp_data<=ALUF2;
            when "0110"=> disp_data<=reg_out2;
            when "0101" => disp_data<=x"0000"; disp_data(12)<=JMP; disp_data(8)<=BEQ; disp_data(4)<=(BEQ and ALUZERO) or (BNE and not ALUZERO) or (not ALUOUT(15) and BGEZ); disp_data(0)<=BGEZ;
            when "0111" => disp_data<=PC;
            when "1000" => disp_data<=ram(0);
            when "1001" => disp_data<=ram(1);
            when "1010" => disp_data<=ram(2);
            when "1011" => disp_data<=ram(3);
            when "1100" => disp_data<=ram(4);
            when "1101" => disp_data<=ram(5);
            when "1110" => disp_data<=read_reg_3;
            when "1111" => disp_data<=ram(10);
            when others => disp_data<="0000000000000000";
        end case;
    end process;
end Behavioral;