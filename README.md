# JavaBars

Note: The microworld JavaBars is part of the research project "Children's Construction of the Rational Numbers of Arithmetic," NSF-Grant No. RED-8954678 (principal investigators Dr. Leslie Steffe and Dr. John Olive, University of Georgia). The original programmer for JavaBars is Barry Biddlecomb.

## Introduction

JavaBars is a rectangle microworld designed for conceptual work with fractions. The Fraction Project research team has worked with a similar microworld, TIMABars, with students in third, fourth, and fifth grade. TIMABars was an ObjectLogo program, and Object Logo has not yet been adapted to the new Macintosh and PC operating systems. JavaBars is programmed in the Java programming language and thus will work for PC and for Macintosh platforms. The program still has some bugs that will be corrected in time, we hope! Please be patient.

I am introducing JavaBars as one environment in which students can explore and work with fractions. JavaBars is not a drill-and-practice environment and not meant to practice the procedural rules! But hopefully students will build connections between their work in the microworld and their work in the classroom, the textbook, and worksheets. My ultimate goal is that students make sense of what they are learning and have the skills to execute procedures efficiently.

### How to Load JavaBars

JavaBars needs a Java Runtime Environment (JRE) in order to run.

### How to End a JavaBars Session

Close the window.

### The JavaBars Screen

The screen is divided into two parts: (a) the control area and (b) the working area (candy bar shop). The buttons in the control area are divided into four groups:

- the top row of buttons controls actions that are applied to whole bars;
- the buttons in the Parts area control actions applied to equal parts of a bar;
- the buttons in the Pieces area control actions applied to pieces of a bar; these pieces might or might not have equal size; and
- the buttons in the bottom row control actions applied to the file.

I will address the actions of each button in the glossary.

## Glossary

### The Buttons in the Top Row

#### The Bar button

Activates or deactivates the Bar-drawing function.

- Click the Bar button, it changes color. Trace a rectangle in the working space. You will get a red rectangular region. You can continue to produce bars as long as the button is highlighted. When you are done, click off the button or highlight another button.
- At this point, the rectangle will only extend to the right and down, that is, you cannot backtrack.

#### The COVER button

Activates or deactivates the COVER-drawing function.

- Click the COVER button, it changes color. Trace a rectangle in the working space. You will get a gray rectangular region. You can continue to produce covers as long as the button is highlighted.
- Click off the button (or highlight another button) when you are done.
- You can move, copy, and erase covers. You cannot fill covers with a different color.
- A bar will always slide below a cover.

#### The MAT button

Activates or deactivates the MAT-drawing function.

- Click the MAT button, it changes color. Trace a rectangle in the working space. You will get a pink rectangular region. You can continue to produce mats as long as the button is highlighted.
- Click off the button (or highlight another button) when you are done.
- You can move, copy, and erase mats. You cannot fill mats with a different color.
- A bar will always slide above the mat.

#### The ERASE button

Activates or deactivates the ERASE function.

- Click the ERASE button, it changes color. Click on the bar, mat, or cover you want to erase.
- Alternately, trace a rectangle around the pieces (bars, mats, covers) you want to erase. Everything in the rectangle will be erased.
- Click off the button (or highlight another button) when you are done.

#### The COPY button

Activates or deactivates the COPY function.

- Click the Copy button, then click of the object (bar, cover, mat) you want to copy. A light blue outline appears around the object.
- Click in the workspace and a copy of your object appears. Continue clicking in the workspace as long as you want to copy the chosen object.
- Click off the button (or highlight another button) when you are done.

#### The JOIN button

Activates or deactivates the JOIN function.

- Click the Join button, then one first piece; a blue frame appears. Next click on the second piece close to the side where you want to join.
- When the two pieces are joined together, a yellow line appears at the place where the two bars are joined. This line cannot not be erased, and it has no function. (That is, you can’t break the bar along the yellow line. It is a bug that needs to be fixed.)
- In order to join two pieces, they have to have the same dimension along the side you want to join. That is, they have to have the same height or the same width.
- So deactivate the JOIN button, either click the button itself, another button, or just click on an empty spot in the workspace.

#### The CUT button

Activates or deactivates the CUT function.

- Click the CUT button, then click on the bar.
- If you want to cut the bar horizontally move the pointer horizontally to the right or left and click. If you want to cut the bar vertically move the pointer vertically up or down and click.
- At times, a move of the pointer followed click had unexpected results, that is, sometimes I had to click once, at other times twice. I still have to figure out the rule.
- To terminate the CUT function, click the button, activate a different function, or click in the workspace.

#### The FILL button

Activates or deactivates the FILL function.

- The default color for a new bar is red.
- Click the FILL button, a window appears.
- Select a color and click the OK button.
- Click the bar you want to fill with the chosen color. You can move from bar to bar, the Fill function stays active unless you click on the workspace.
- To terminate the FILL function click the button or somewhere in the workspace.

#### The REPEAT button.

A bar can be repeated vertically or horizontally.

- (It seems that this function still has a bug because it seems to be able to repeat in both directions, see the step-shaped figure in Figure 5. Some of the pieces are mirages, that is, they are not really there. For example, you cannot mark them. However, if you use the BREAK function from the Parts menu, they all turn into bars, including the mirages.)
- Click the REPEAT button and then click on the bar. A blue frame appears.
- Move the pointer either horizontally or vertically and click. The direction of your move tells the computer in which direction to repeat.
- To turn off REPEAT, click on the button; this deselects the bar. Then click anywhere on the screen or choose another function. This deselects the button.

#### The SET UNIT BAR button

Selects a bar as your whole.

- Click on the SET UNIT BAR button, then on the bar you designate as your whole.
- The label “Unit Bar” appears in the bar.
- If you select a different bar as your unit bar (whole), the label disappears from your previous choice.
- If you save a screen and later load it, the UNITBAR function is no longer active, although you still see the label. You have to re-select the bar as your unit bar.

#### The MEASURE button

Measures a bar in terms of the designated unit bar.

- Click on the MEASURE button, then on the bar you want to measure.
- The MEASURE function will always return a fraction (proper or improper) in lowest terms.
- The MEASURE function can only be applied to multiples of the unit bar or to parts created with the PARTS functions.
- The LABEL button. Assigns a label to a bar.
- Activate the Label button, the choose a bar. A window appears.
- Place the cursor in the writing area and type your label.
- Click OK, and your label appears on the bar.
- The label replaces a previous MEASURE.

#### The GROUP button. Collects bars into a group.

The Group function is a practical rather than a conceptual function.

- Once bars are grouped you can move all of them at the same time rather then move every bar by itself.
- Click the GROUP button, then successively the bars you want to group.
- To terminate the GROUP function, deselect the button or select a different function.

#### The UNGROUP button

Ungroups previously grouped bars.

- Click the UNGROUP button, then the group of bars.

### The Buttons in the PIECES Menu

- When you use the buttons in this menu, you, the user, determine the size of the pieces. Your pieces may or may not be of equal size.

#### The UP/DOWN and LEFT/RIGHT buttons

Determines whether the bar is marked horizontally or vertically.

- Click the button to turn it on; click it again to turn it off.
- With the UP/DOWN button or the LEFT/RIGHT button turned on, click on a bar. A white line appears at the tip of the pointer. The bar is divided into two pieces, but with the bar still intact.
- The PIECES and BAR buttons. Determines whether your operations effect the whole bar or just a piece of the bar.
- These two buttons work like radio buttons, that is, you turn one of the buttons off by turning the other one on.

#### The BREAK button

Activates and deactivates the BREAK function.

- Highlight the BREAK button, then click on a bar that is marked. The bar is broken into its pieces.
- Each piece is now a bar in its own right (Figure 8).

#### The FILL button

Activates and deactivates the FILL PIECES function.

- Activate the FILL button, the same window as in the general FILL function appears.
- Choose a color, then click the OK button.
- Click on the piece you want to fill.
- If you want to fill the next piece with a different color, as shown in Figure 9, you have to turn the FILL button off and on again.
- Otherwise, click on the next piece you want to fill.

#### The CLEAR button

Activates or deactivates the CLEAR function.

- Click on the CLEAR button, then anywhere on the bar.
- All marks are erased from the bar.
- The PULLOUT button. Activates or deactivates the PULLOUT function.
- Activate the PULLOUT function, then click on the piece you want to pull off from the bar. Deactivate the PULLOUT function. Grab the piece and move if off the bar. The original bar is still intact.
- The piece can now be used to check whether your mark or marks divide the bar into pieces of equal size. In Figure 10, for example, the bar was supposed to be divided into two equal pieces. The turquoise piece, however, is larger than the red piece.
- You can pull out several pieces: Click on each piece you want to pull out, then deactivate the function and move the pieces (Figure 10).

#### The COMBINE button

Activates and deactivates the COMBINE function.

- Erases selected marks from a bar.
- Activate the COMBINE function. Click on the pieces you want to combine.
- Left bar in second row: With the COMBINE function activated, I clicked on piece 1, then on piece 2. I then clicked on the combined part, then on piece 3.
- Middle bar in lower row: With the COMBINE function activated, I clicked on piece 1, then on piece 4. I turned off COMBINE function and turned it on again. In then clicked on piece 2, then on piece 3.
- The difference between the two bars is that in the left, three pieces are combined to one; and in the middle bar, two pairs of pieces are combined.
- Right bar in lower row: The bar was broken in to its constituent pieces. The JOIN function was used in order to change three pieces into one.

### The Buttons in the PARTS Menu

When you use the buttons in this menu, the computer calculates the size of the parts. The parts from any one action will be of equal size.

#### The UP/DOWN and LEFT/RIGHT buttons.

Determines whether the bar is marked horizontally or vertically.

- Click the button to turn it on; click it again to turn it off.
- With the UP/DOWN button or the LEFT/RIGHT button activated, choose the number of parts and click on a bar. One or more yellow lines appear. The bar is divided into the indicated number of parts, but the bar is still intact.

#### The PARTS and BAR buttons

Determines whether your operations effect the whole bar or just a part of the bar.

- These two buttons work like radio buttons, that is, you turn one of the buttons off by turning the other one on.

#### The BREAK, CLEAR, COMBINE, FILL, and PULLOUT button work the same way as in the PIECES menu.

### The File Control Buttons

Not all buttons in this row are functional at this time.

#### The NEW button

Click the NEW button, a window appears. For some reason, not all of the window shows; you can enlarge it the same way you change the size of any window (see Figure 14). If you choose “yes,” your work will be cleared and you get a clear screen.

#### The SAVE button

Click the SAVE button, a familiar window appears. Select the folder and name your file. Follow the rules of your operating system.
As a Mac user, you can also save your work by taking a picture. You have to press three buttons at the same time: shift, apple, and 3. You hear the same sound as if you are taking a picture with your camera. The pictures appear on your hard drive and are listed as Picture 1, Picture 2, etc. All picture in this manual are taken this way. I also took many pictures in my work with Joshua in order to preserve and remember his progress. However, these pictures are static, you cannot load them into the program and continue your work.

#### The LOAD button

Click the LOAD button, a familiar window appears. Select the folder and name that identify your file. Follow the rules of your operating system.

#### The CONFIGURE button

Once the CONFIGURE button is functional you will be able to turn off functions of the program temporarily.
