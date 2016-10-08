/*
 * Copyright (C) 2016 Oleg Kan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.simplaapliko.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ListItemAdapter extends SelectableAdapter<ListItem, ListItemAdapter.ViewHolder> {

    public ListItemAdapter(List<ListItem> data, SelectableAdapter.ViewHolder.ClickListener clickListener) {
        setData(data);
        mClickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view, mClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListItem model = getData().get(position);

        holder.mNameView.setText(model.getName());
        holder.mDescriptionView.setText(model.getDescription());

        holder.mSelectedOverlay.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    public static class ViewHolder extends SelectableAdapter.ViewHolder {
        View mSelectedOverlay;
        TextView mNameView;
        TextView mDescriptionView;

        public ViewHolder(View itemView, ClickListener clickListener) {
            super(itemView, clickListener);

            mSelectedOverlay = itemView.findViewById(R.id.selected_overlay);
            mNameView = (TextView) itemView.findViewById(R.id.name);
            mDescriptionView = (TextView) itemView.findViewById(R.id.description);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }
    }
}
