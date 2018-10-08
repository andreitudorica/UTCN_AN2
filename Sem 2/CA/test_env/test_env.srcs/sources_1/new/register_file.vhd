
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity reg_file is
    port (
    clk : in std_logic;
    ra1 : in std_logic_vector (3 downto 0);
    ra2 : in std_logic_vector (3 downto 0);
    wa : in std_logic_vector (3 downto 0);
    wd : in std_logic_vector (15 downto 0);
    wen : in std_logic;
    rd1 : out std_logic_vector (15 downto 0);
    rd2 : out std_logic_vector (15 downto 0)
    );
end reg_file;
architecture Behavioral of reg_file is
        type reg_array is array (0 to 15) of std_logic_vector(15 downto 0);
        signal reg_file : reg_array:=("0000000000000001","0000000000000010","0000000000000011","0000000000000100",others =>"0000000000000000");
    begin
        process(clk)
            begin
            if rising_edge(clk) then
                if wen = '1' then
                    reg_file(conv_integer(wa)) <= wd;
                end if;
            end if;
        end process;
        rd1 <= reg_file(conv_integer(ra1));
        rd2 <= reg_file(conv_integer(ra2));
end Behavioral; 