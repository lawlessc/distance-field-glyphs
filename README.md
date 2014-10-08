distance-field-glyphs
=====================

A package for using signed distance field fonts/glyphs in JPCT-AE.

You can generate sdf fonts using Paul Houxs signed distance field generator

https://github.com/paulhoux/Cinder-Samples/tree/master/TextRendering 

1.The first step is to use Paul Houx font converter with the font you want.
  This will generate a PNG file of the Glyphs in SDF form and a file containing their positions and offsets etc.
  
2.Add these files to your JPCT project.

3.Add the SDFLibrary project to your workspace and to your program as a library.


You can then create the Factory on starting your application and use it to add fonts
to the application.








![Preview](https://github.com/lawlessc/distance-field-glyphs/blob/master/preview.png)




The font's used come from
==========================================================
http://www.geo.sbg.ac.at/borromae/rk_afont.htm 
http://www.dafont.com/rongorongo.font

the latin script font is the Google Roboto font.



Some possible problems that should be noted at the moment:
==========================================================




* I haven't yet done anything to handle changes in screen ortientation.so rotating your device will likely leave you
with some very skewed text.

* I need to add code for removing and changing text too.

* The code for this is messy and unoptimized now with many poorly named variables.
