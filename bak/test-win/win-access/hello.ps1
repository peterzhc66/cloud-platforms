param($who="world")
write-host "hello ${who} from powershell"
ipconfig /all
winrm e winrm/config/listener
