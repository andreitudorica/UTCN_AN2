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
    port (clk : in std_logic;ra1 : in std_logic_vector (3 downto 0);ra2 : in std_logic_vector (3 downto 0);wa : in std_logic_vector (3 downto 0);wd : in std_logic_vector (15 downto 0);wen : in std_logic; rd1 : out std_logic_vector (15 downto 0);rd2 : out std_logic_vector (15 downto 0));
        end component;
    
    component SSD is
        Port (nr1,nr2,nr3,nr4: in std_logic_vector(3 downto 0); clk: in std_logic;   cat: out std_logic_vector(6 downto 0);  an: out std_logic_vector(3 downto 0));
    end component;
    
    signal PC   : std_logic_vector(15 downto 0):=x"0000";
    type mem is array (0 to 255) of std_logic_vector(15 downto 0);
    signal ram: mem:=(others =>x"0000");
    signal rom: mem:=("0000000000000111",
                      "0010000000000101",
                      "0000010010010111",
                      "0110010000000000",
                      "0000000000000111",
                      "0010000000000001",
                      "0110010000000001",
                      "0000000000000111",
                      "0010000000000011",
                      "0110010000000010",
                      "0000000000000111",
                      "0010000000000010",
                      "0110010000000011",
                      "0000000000000111",
                      "0010000000000100",
                      "0110010000000100",
                      "0110010010000101",
                      "0100010000000000",
                      "0100000110000001",
                      "0000010010010111",
                      "0010010010000001",
                      "0100010100000000",
                      "0000100111000001",
                      "1011000000000110",
                      "0010010010000001",
                      "1100010001111100",
                      "0110000110000000",
                      "0000110110110111",
                      "0010100110000000",
                      "1000110111111011",
                      others =>x"0000");
    signal en_cnt,cnt_rst,REGDST,ALUSRC,BRANCH,BGEZ,JMP,MEMW,MEMtoREG,REGW:std_logic;
    signal wd,reg_out1,reg_out2,mux_mid,mux_top,plus,j_address,br_address: std_Logic_vector(15 downto 0);
    signal ram_in,ram_out,disp_data,INSTRUCTION:std_logic_vector(15 downto 0);
    signal ALUCTRL:std_logic_vector(2 downto 0);
    signal ALUF1,ALUF2,ALUOUT:std_logic_vector(15 downto 0);
    signal IMMEDIATE:std_logic_vector(15 downto 0);
    signal ALUZERO,SHIFT_AMOUNT: std_logic;
begin
    ssd1: SSD Port map( nr1=>disp_data(3 downto 0),    nr2=>disp_data(7 downto 4),    nr3=>disp_data(11 downto 8),    nr4=>disp_data(15 downto 12), clk=>clk,   cat=>cat,   an=>an);
    mpg0: mpg Port map ( clk     => clk,btn     => btn(0), step    => en_cnt);
    mpg3: mpg Port map ( clk     => clk,btn     => btn(3), step    => cnt_rst);
    rf1: reg_file Port map(clk => clk,ra1 => PC(3 downto 0),ra2 => PC(3 downto 0),   wa => PC(3 downto 0),  wd => wd,  wen => REGW,   rd1 => reg_out1,   rd2 => reg_out2 );
    process(clk)
    begin
        if(clk'event and clk='1') then
            if en_cnt = '1' then
                PC <=mux_top;
            end if;             
        end if;
        if( cnt_rst='1') then
            PC<=x"0000";
        end if;
    end process; 
    
     plus<='1' + PC;
    process(BRANCH, br_address, plus) 
    begin 
       if BRANCH = '1' then 
           mux_mid<=br_address;
       else
          mux_mid<=plus;
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
   INSTRUCTION<=rom(conv_integer(PC(15 downto 0)));
    
    --ram
    process(clk,btn)
    begin
        if(clk'event and clk='1')  then
            if(MEMW='1') then
                ram_in<=reg_out2;  
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
            when "000" =>  REGDST<='1'; ALUSRC<='0'; BRANCH<='0'; BGEZ<='0'; JMP<='0'; MEMW<='0'; MEMtoREG<='0'; REGW<='1'; ALUCTRL<= INSTRUCTION(2 downto 0); IMMEDIATE<="0000000000000000"; SHIFT_AMOUNT<=INSTRUCTION(3);
            when "001" =>  REGDST<='0'; ALUSRC<='1'; BRANCH<='0'; BGEZ<='0'; JMP<='0'; MEMW<='0'; MEMtoREG<='0'; REGW<='1'; ALUCTRL<= "000"; IMMEDIATE(6 downto 0)<=INSTRUCTION(6 downto 0); IMMEDIATE(15 downto 7)<=(others=>INSTRUCTION(6));
            when "010" =>  REGDST<='0'; ALUSRC<='1'; BRANCH<='0'; BGEZ<='0'; JMP<='0'; MEMW<='0'; MEMtoREG<='1'; REGW<='1'; ALUCTRL<= "000"; IMMEDIATE(6 downto 0)<=INSTRUCTION(6 downto 0); IMMEDIATE(15 downto 7)<=(others=>INSTRUCTION(6));
            when "011" =>  REGDST<='0'; ALUSRC<='1'; BRANCH<='0'; BGEZ<='0'; JMP<='0'; MEMW<='1'; MEMtoREG<='0'; REGW<='1'; ALUCTRL<= "000"; IMMEDIATE(6 downto 0)<=INSTRUCTION(6 downto 0); IMMEDIATE(15 downto 7)<=(others=>INSTRUCTION(6));
            when "100" =>  REGDST<='0'; ALUSRC<='1'; BRANCH<='1'; BGEZ<='0'; JMP<='0'; MEMW<='0'; MEMtoREG<='0'; REGW<='0'; ALUCTRL<= "001"; IMMEDIATE(6 downto 0)<=INSTRUCTION(6 downto 0); IMMEDIATE(15 downto 7)<=(others=>INSTRUCTION(6));
            when "101" =>  REGDST<='0'; ALUSRC<='1'; BRANCH<='0'; BGEZ<='1'; JMP<='0'; MEMW<='0'; MEMtoREG<='0'; REGW<='0'; ALUCTRL<= "000"; IMMEDIATE(6 downto 0)<=INSTRUCTION(6 downto 0); IMMEDIATE(15 downto 7)<=(others=>INSTRUCTION(6));
            when "110" =>  REGDST<='0'; ALUSRC<='1'; BRANCH<='1'; BGEZ<='0'; JMP<='0'; MEMW<='0'; MEMtoREG<='0'; REGW<='0'; ALUCTRL<= "001"; IMMEDIATE(6 downto 0)<=INSTRUCTION(6 downto 0); IMMEDIATE(15 downto 7)<=(others=>INSTRUCTION(6));
            when "111" =>  REGDST<='0'; ALUSRC<='1'; BRANCH<='0'; BGEZ<='0'; JMP<='1'; MEMW<='0'; MEMtoREG<='0'; REGW<='0'; ALUCTRL<= "000"; IMMEDIATE(6 downto 0)<=INSTRUCTION(6 downto 0); IMMEDIATE(15 downto 7)<=(others=>INSTRUCTION(6));
        end case;
    end process;
    
    --ALU
    process(ALUCTRL,ALUSRC)
        begin
            ALUF1<=reg_out1;
            if(ALUSRC='0') then
                ALUF2<=reg_out2;
            else 
                ALUF2<=IMMEDIATE;
                end if;
            case (ALUCTRL) is
                when "000" => ALUOUT <= ALUF1 + ALUF2;--add
                when "001" => ALUOUT <= ALUF1 - ALUF2;--substract
                when "010" => if(SHIFT_AMOUNT='1') then ALUOUT <= ALUF1(14 downto 0)&'0'; else ALUOUT<=ALUF1; end if;--shift left logical
                when "011" => if(SHIFT_AMOUNT='1') then ALUOUT <= ALUF1(14 downto 0)&'0'; else ALUOUT<=ALUF1; end if;--shift right logical
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
    
    
    
    --mux display
    process(sw(2 downto 0))
        begin 
        case sw(2 downto 0) is
            when "000" => disp_data<=INSTRUCTION;
            when "001" => disp_data(15 downto 0)<=ALUOUT;
            when "010" => disp_data(15 downto 0)<=wd;
            
            when "111" => disp_data<=PC;
            when others => disp_data<="0000000000000000";
        end case;
    end process;
end Behavioral;
