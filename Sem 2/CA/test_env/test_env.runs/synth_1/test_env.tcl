# 
# Synthesis run script generated by Vivado
# 

set_msg_config -id {HDL 9-1061} -limit 100000
set_msg_config -id {HDL 9-1654} -limit 100000
create_project -in_memory -part xc7a35tcpg236-1

set_param project.singleFileAddWarning.threshold 0
set_param project.compositeFile.enableAutoGeneration 0
set_param synth.vivado.isSynthRun true
set_property webtalk.parent_dir D:/30421/TudoricaAndrei/test_env/test_env.cache/wt [current_project]
set_property parent.project_path D:/30421/TudoricaAndrei/test_env/test_env.xpr [current_project]
set_property default_lib xil_defaultlib [current_project]
set_property target_language Verilog [current_project]
set_property ip_output_repo d:/30421/TudoricaAndrei/test_env/test_env.cache/ip [current_project]
set_property ip_cache_permissions {read write} [current_project]
read_vhdl -library xil_defaultlib {
  D:/30421/TudoricaAndrei/test_env/test_env.srcs/sources_1/new/register_file.vhd
  D:/30421/TudoricaAndrei/test_env/test_env.srcs/sources_1/new/SSD.vhd
  D:/30421/TudoricaAndrei/test_env/test_env.srcs/sources_1/new/mpg.vhd
  D:/30421/TudoricaAndrei/test_env/test_env.srcs/sources_1/new/test_env.vhd
}
foreach dcp [get_files -quiet -all *.dcp] {
  set_property used_in_implementation false $dcp
}
read_xdc D:/30421/TudoricaAndrei/test_env/test_env.srcs/constrs_1/imports/test_env/basys3.xdc
set_property used_in_implementation false [get_files D:/30421/TudoricaAndrei/test_env/test_env.srcs/constrs_1/imports/test_env/basys3.xdc]


synth_design -top test_env -part xc7a35tcpg236-1


write_checkpoint -force -noxdef test_env.dcp

catch { report_utilization -file test_env_utilization_synth.rpt -pb test_env_utilization_synth.pb }
