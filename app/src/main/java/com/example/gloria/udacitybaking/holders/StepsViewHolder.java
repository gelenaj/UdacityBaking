package com.example.gloria.udacitybaking.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.gloria.udacitybaking.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsViewHolder extends RecyclerView.ViewHolder {
  @BindView(R.id.step)
  public TextView step;

    @BindView(R.id.step_order)
    public TextView stepOrder;




    public StepsViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}