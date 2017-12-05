package com.math.cky.matrixcalculator.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView.ScaleType;

import com.bumptech.glide.Glide;
import com.math.cky.matrixcalculator.view.SmoothImageView;

public class SpaceImageDetailActivity extends AppCompatActivity {

	private int img;
	private int mLocationX;
	private int mLocationY;
	private int mWidth;
	private int mHeight;
	SmoothImageView imageView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		img = getIntent().getIntExtra("image",0);
		mLocationX = getIntent().getIntExtra("locationX", 0);
		mLocationY = getIntent().getIntExtra("locationY", 0);
		mWidth = getIntent().getIntExtra("width", 0);
		mHeight = getIntent().getIntExtra("height", 0);

		imageView = new SmoothImageView(this);
		imageView.setOriginalInfo(mWidth, mHeight, mLocationX, mLocationY);
		imageView.transformIn();
		imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		imageView.setScaleType(ScaleType.FIT_CENTER);
		setContentView(imageView);

		//加载图片
		Glide.with(this).load(img).asBitmap().into(imageView);

		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

	}

	@Override
	public void onBackPressed() {
		imageView.setOnTransformListener(new SmoothImageView.TransformListener() {
			@Override
			public void onTransformComplete(int mode) {
				if (mode == 2) {
					finish();
				}
			}
		});
		imageView.transformOut();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (isFinishing()) {
			overridePendingTransition(0, 0);
		}
	}

}
