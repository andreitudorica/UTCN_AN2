library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;


entity SSD is
    Port (nr1,nr2,nr3,nr4: in std_logic_vector(3 downto 0);
          clk: in std_logic;
          cat: out std_logic_vector(6 downto 0);
          an: out std_logic_vector(3 downto 0));
end SSD;

architecture Behavioral of SSD is
signal mux1_out: std_logic_vector(3 downto 0);
signal counter_out: std_logic_vector(15 downto 0) := x"0000";
begin
    process(clk)
    begin
        if(rising_edge(clk)) then
            counter_out <= counter_out + 1;
        end if;
    end process;
    
     process(counter_out)
     begin
        case counter_out(15 downto 14) is
            when "00" => an <= "1110";
            when "01" => an <= "1101";
            when "10" => an <= "1011";
            when "11" => an <= "0111";
            end case;
     end process; 
    
    process(counter_out)
    begin
        case counter_out(15 downto 14) is
            when "00" => mux1_out <= nr1;
            when "01" => mux1_out <= nr2;
            when "10" => mux1_out <= nr3;
            when "11" => mux1_out <= nr4;
        end case;
    end process;
    
    process(mux1_out)
    begin
        case mux1_out is 
            when "0000" => cat <= "1000000"; 
            when "0001" => cat <= "1111001"; 
            when "0010" => cat <= "0100100"; 
            when "0011" => cat <= "0110000"; 
            when "0100" => cat <= "0011001"; 
            when "0101" => cat <= "0010010"; 
            when "0110" => cat <= "0000010"; 
            when "0111" => cat <= "1111000"; 
            when "1000" => cat <= "0000000"; 
            when "1001" => cat <= "0010000";
            when "1010" => cat <= "0001000"; 
            when "1011" => cat <= "0000011"; 
            when "1100" => cat <= "1000110"; 
            when "1101" => cat <= "0100001"; 
            when "1110" => cat <= "0000110"; 
            when "1111" => cat <= "0001110";    
        end case;
    end process;
    
end Behavioral;
