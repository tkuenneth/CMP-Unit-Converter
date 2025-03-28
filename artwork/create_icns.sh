#!/bin/sh
rsvg-convert -w 1024 -h 1024 artwork_desktop.svg -o artwork_desktop_1024x1024.png

mkdir app_icon_desktop.iconset
sips -z 16 16     artwork_desktop_1024x1024.png --out app_icon_desktop.iconset/icon_16x16.png
sips -z 32 32     artwork_desktop_1024x1024.png --out app_icon_desktop.iconset/icon_16x16@2x.png
sips -z 32 32     artwork_desktop_1024x1024.png --out app_icon_desktop.iconset/icon_32x32.png
sips -z 64 64     artwork_desktop_1024x1024.png --out app_icon_desktop.iconset/icon_32x32@2x.png
sips -z 32 32     artwork_desktop_1024x1024.png --out app_icon_desktop.iconset/icon_48x48.png
sips -z 64 64     artwork_desktop_1024x1024.png --out app_icon_desktop.iconset/icon_48x48@2x.png
sips -z 128 128   artwork_desktop_1024x1024.png --out app_icon_desktop.iconset/icon_128x128.png
sips -z 256 256   artwork_desktop_1024x1024.png --out app_icon_desktop.iconset/icon_128x128@2x.png
sips -z 256 256   artwork_desktop_1024x1024.png --out app_icon_desktop.iconset/icon_256x256.png
sips -z 512 512   artwork_desktop_1024x1024.png --out app_icon_desktop.iconset/icon_256x256@2x.png
sips -z 512 512   artwork_desktop_1024x1024.png --out app_icon_desktop.iconset/icon_512x512.png
cp artwork_desktop_1024x1024.png app_icon_desktop.iconset/icon_512x512@2x.png
iconutil -c icns app_icon_desktop.iconset
rm -R app_icon_desktop.iconset
rm artwork_desktop_1024x1024.png

# iOS
rsvg-convert -w 1024 -h 1024 artwork_full_background.svg -o ../iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/app-icon-1024.png
