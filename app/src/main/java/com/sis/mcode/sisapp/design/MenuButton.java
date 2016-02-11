package com.sis.mcode.sisapp.design;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.util.Locale;

import com.sis.mcode.sisapp.R;
import com.sis.mcode.sisapp.application.SisApp;
import com.sis.mcode.sisapp.font.Font;

public class MenuButton extends View {
    private String menuTitle = new String();
    private String menuTitleL1 = new String();
    private String menuTitleL2 = new String();

    private int menuColor = 0;
    private int cols = 2;

    private Paint backgroundPaint;

    private Paint titlePaint;
    private Paint titleL2Paint;

    private int numlines = 1;

    // Elements dimensions
    private int maxSize; // Box size
    public float fontsize;
    public float textYPos;

    public MenuButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MenuButton, 0, 0);

        try
        {
            menuTitle = a.getString(R.styleable.MenuButton_menuTitle);
            menuColor = a.getInteger(R.styleable.MenuButton_menuColor, 0);
        }
        finally
        {
            a.recycle();
        }
    }


    public MenuButton(Context context)
    {
        super(context);
        init();
    }

    public MenuButton(Context context, String title, String color, int cols)
    {
        super(context);
        this.menuTitle = title;
        this.menuColor = Color.get_color(color);
        this.cols = cols;
        init();
    }

    private void init ()
    {

        int numcols = this.cols;
        WindowManager wm = (WindowManager) SisApp.getAppContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        int width = display.getWidth();
        int rightMargin = 10;

        if (numcols == 1){
            maxSize = Math.round((width/2))-rightMargin;
        }else {
            maxSize = Math.round((width / numcols)) - rightMargin;
        }
        //maxSize = Math.round((width/numcols))-rightMargin;
        fontsize = maxSize * 0.095f;

        titlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        titlePaint.setTypeface(Font.get_font("bold condensed"));
        titlePaint.setTextSize(fontsize);
        titlePaint.setColor(0xffffffff);
        titlePaint.setTextAlign(Paint.Align.LEFT);

        menuTitle = menuTitle.toUpperCase(Locale.getDefault());
        float textWidth = titlePaint.measureText(menuTitle);
        //if (textWidth>maxSize*0.9) numlines=2;

        if (numlines == 1)
            textYPos = maxSize*0.160f;
        else
            textYPos = maxSize*0.130f;

        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(menuColor);
        titleL2Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        titleL2Paint.setTypeface(Font.get_font("bold condensed"));
        titleL2Paint.setTextSize(fontsize);
        titleL2Paint.setColor(0xffffffff);
        titleL2Paint.setTextAlign(Paint.Align.CENTER);


        if (numlines==1)
            menuTitleL1 = menuTitle;
        else
        {
            String[] menuPieces = menuTitle.split(" ");

            if (menuPieces.length == 2)
            {
                menuTitleL1 = menuPieces[0];
                menuTitleL2 = menuPieces[1];
            }
            else
            {
                boolean newLine = false;

                for (int c=0; c<menuPieces.length; c++)
                {
                    if (!newLine)
                        menuTitleL1 += menuPieces[c]+" ";
                    else
                        menuTitleL2 += menuPieces[c]+" ";

                    float lineW = titlePaint.measureText(menuTitleL1.trim());
                    if (!newLine && lineW > textWidth * 0.4) newLine = true;
                }
                menuTitleL1 = menuTitleL1.trim();
                menuTitleL2 = menuTitleL2.trim();
            }
        }
    }


    public String getMenuTitle()
    {
        return menuTitle;
    }

    public void setMenuTitle(String title)
    {
        menuTitle=title;
        invalidate();
        requestLayout();
    }

    public int getMenuColor()
    {
        return menuColor;
    }

    public void setMenuColor(int idColor)
    {
        menuColor = idColor;
        invalidate();
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            if (cols == 1){
                setMeasuredDimension(maxSize*2+10, maxSize / 3);
            }else{
                setMeasuredDimension(maxSize, maxSize / 3);
            }
    }

    public void onDraw (Canvas canvas)
    {
        super.onDraw(canvas);

        // Draw the background square
        int padding = 10;
        int squareSize = (maxSize-padding*2);
        //int rectSizeBase = (maxSize-padding*2);
        int rectSizeBase = (maxSize);
        //int rectSizeHeight = (maxSize-padding*2)/2;
        int rectSizeHeight = (maxSize);

        /*if (cols == 1){
            rectSizeBase = rectSizeBase*2+10;
            canvas.drawRect(padding, 0, rectSizeBase, rectSizeHeight, backgroundPaint);
        }else{
            canvas.drawRect(padding, 0, rectSizeBase, rectSizeHeight, backgroundPaint);
        }*/

        canvas.drawRoundRect(new RectF(20, 0, canvas.getWidth()-20, canvas.getHeight()), 40, 40, backgroundPaint);

        Rect bounds = new Rect();
        titlePaint.getTextBounds(menuTitleL1, 0, menuTitleL1.length(), bounds);
        int x = (canvas.getWidth() / 2) - (bounds.width() / 2);
        int y = (canvas.getHeight() / 2) + (bounds.height() / 2);

        //canvas.drawText(menuTitleL1, x, textYPos, titlePaint);
        canvas.drawText(menuTitleL1, x, y, titlePaint);

        //canvas.drawText(menuTitleL1, squareSize/6, textYPos, titlePaint);

        if (numlines == 2) {
            canvas.drawText(menuTitleL2, squareSize/6, textYPos+fontsize+maxSize*0.02f, titleL2Paint);
        }
    }
}