/***
 Copyright (c) 2015 CommonsWare, LLC
 Licensed under the Apache License, Version 2.0 (the "License"); you may not
 use this file except in compliance with the License. You may obtain	a copy
 of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
 by applicable law or agreed to in writing, software distributed under the
 License is distributed on an "AS IS" BASIS,	WITHOUT	WARRANTIES OR CONDITIONS
 OF ANY KIND, either express or implied. See the License for the specific
 language governing permissions and limitations under the License.

 From _The Busy Coder's Guide to Android Development_
 https://commonsware.com/Android
 */

package com.commonsware.android.recyclerview.actionmodelist2;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

class RowController extends RecyclerView.ViewHolder
    implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
  private ChoiceCapableAdapter adapter;
  private TextView label=null;
  private TextView size=null;
  private ImageView icon=null;
  private String template=null;
  private CheckBox cb=null;

  RowController(ChoiceCapableAdapter adapter, View row) {
    super(row);

    this.adapter=adapter;
    label=(TextView)row.findViewById(R.id.label);
    size=(TextView)row.findViewById(R.id.size);
    icon=(ImageView)row.findViewById(R.id.icon);
    cb=(CheckBox)row.findViewById(R.id.cb);

    template=size.getContext().getString(R.string.size_template);

    row.setOnClickListener(this);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      row.setOnTouchListener(new View.OnTouchListener() {
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean onTouch(View v, MotionEvent event) {
          v
              .findViewById(R.id.row_content)
              .getBackground()
              .setHotspot(event.getX(), event.getY());

          return(false);
        }
      });
    }

    cb.setOnCheckedChangeListener(this);
  }

  @Override
  public void onClick(View v) {
    Toast.makeText(v.getContext(),
        String.format("Clicked on position %d", getAdapterPosition()),
        Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    adapter.onChecked(getAdapterPosition(), isChecked);
  }

  void bindModel(String item) {
    label.setText(item);
    size.setText(String.format(template, item.length()));

    if (item.length()>4) {
      icon.setImageResource(R.drawable.delete);
    }
    else {
      icon.setImageResource(R.drawable.ok);
    }

    cb.setChecked(adapter.isChecked(getAdapterPosition()));
  }
}
