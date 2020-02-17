adb forward tcp:8080 tcp:8080
adb forward --remove-all


set path adb temporary
'set path=C:\Users\DELL\AppData\Local\Android\Sdk\platform-tools' in terminal


adb.exe kill-server


adb.exe devices(list all devices )

adb -s 123abc12 shell getprop ro.product.model(device model name from adb command)