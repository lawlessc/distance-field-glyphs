distance-field-glyphs
=====================

A package for using signed distance field fonts/glyphs in JPCT-AE. It's based on Paul Houxs implementation of Valves SDF fonts
http://www.valvesoftware.com/publications/2007/SIGGRAPH2007_AlphaTestedMagnification.pdf 

https://forum.libcinder.org/topic/signed-distance-field-font-rendering

However i have converted the shader to something that works with jpct in opengl es.
The shader also uses position data to determine where in the character texture it neets to look
to find the character it needs to display.

You can generate sdf fonts using Paul Houxs signed distance field generator

https://github.com/paulhoux/Cinder-Samples/tree/master/TextRendering 

1.The first step is to use Paul Houx font converter with the font you want.
  This will generate a PNG file of the Glyphs in SDF form and a file containing their positions and offsets etc.
  
2.Add these files to your JPCT project.

3.Add the SDFLibrary project to your workspace and to your program as a library.

I've included a modified version of Egon Olsens HelloShader Project that implements this.

You can then create the Factory on starting your application and use it to add fonts
to the application.








![Preview](https://github.com/lawlessc/distance-field-glyphs/blob/master/preview.png)




The font's used come from
==========================================================
http://www.geo.sbg.ac.at/borromae/rk_afont.htm -  Hieroglyphs
http://www.dafont.com/rongorongo.font          - RongoRongo

the latin script font is the Google Roboto font.



Some possible problems that should be noted at the moment:
==========================================================




* I haven't yet done anything to handle changes in screen ortientation.so rotating your device will likely leave you
with some very skewed text.

* I need to add code for removing and changing text too.

* The code for this is messy and unoptimized now with many poorly named variables.

* I would like to add other things such as text with borders, glow or shadow as outlined in the Valve whitepaper.




License
=======
The MIT License (MIT)

Copyright (c) 2014 Christopher Lawless

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

