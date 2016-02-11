package com.sis.mcode.sisapp;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.sis.mcode.sisapp.entity.TipAfi;

import java.io.File;
import java.util.List;

public class DescActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private SliderLayout mDemoSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desc);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setNavigationIcon(R.mipmap.ic_back);
        myToolbar.setTitle(getIntent().getStringExtra("name"));
        myToolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(myToolbar);

        mDemoSlider = (SliderLayout) findViewById(R.id.slider);

        ContextWrapper cw = new ContextWrapper(DescActivity.this);
        File images = cw.getDir("sisapp", Context.MODE_PRIVATE);

        String name = images + "/" + getIntent().getStringExtra("image");
        TextSliderView textSliderView = new TextSliderView(this);
        textSliderView
             .description(getIntent().getStringExtra("name"))
             .image(new File(name))
             .setScaleType(BaseSliderView.ScaleType.Fit);

        textSliderView.bundle(new Bundle());
        textSliderView.getBundle().putString("extra", name);

        mDemoSlider.addSlider(textSliderView);
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(600000);
        mDemoSlider.addOnPageChangeListener(this);

        TextView text_content = (TextView) findViewById(R.id.content_desc);
        text_content.setText(getIntent().getStringExtra("description"));
        text_content.setMovementMethod(new ScrollingMovementMethod());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

}