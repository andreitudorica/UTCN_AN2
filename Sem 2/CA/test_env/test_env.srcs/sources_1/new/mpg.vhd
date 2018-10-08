library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;


entity mpg is
    Port ( clk : in STD_LOGIC;
           btn : in STD_LOGIC;
           step : out STD_LOGIC);
end mpg;

architecture Behavioral of mpg is
    signal cnt: std_logic_vector(15 downto 0); 
    signal q0 : std_logic;
    signal q1 : std_logic;
    signal q2 : std_logic;
begin

    process(clk)
    begin
        if rising_edge(clk) then
            cnt <= cnt + '1';
        end if;
    end process;
    
    process(clk)
    begin
        if rising_edge(clk) then
            if cnt = x"FFFF" then
                q0 <= btn;
            end if;
        end if;
    end process;
    
    process(clk)
    begin
        if rising_edge(clk) then
            q1 <= q0;
            q2 <= q1;
        end if;
    end process;

    step <= q1 and not q2; 
    
end Behavioral;

