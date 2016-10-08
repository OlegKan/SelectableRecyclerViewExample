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

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class ListItemAdapter extends SelectableAdapter<ListItemAdapter.ViewHolder> {

    private List<ListItem> mData;
    private ListItemAdapter.ViewHolder.ClickListener mClickListener;

    public ListItemAdapter(List<ListItem> data, ListItemAdapter.ViewHolder.ClickListener clickListener) {
        mData = data;
        mClickListener = clickListener;
    }

    @Override
    public ListItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view, mClickListener);
    }

    @Override
    public void onBindViewHolder(ListItemAdapter.ViewHolder holder, int position) {
        ListItem model = mData.get(position);

        holder.mNameView.setText(model.getName());
        holder.mDescriptionView.setText(model.getDescription());

        holder.mSelectedOverlay.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<ListItem> data) {
        mData = data;
    }

    public void deleteSelectedItems() {
        int[] selectedItems = getSelectedItems();
        Arrays.sort(selectedItems);

        for (int i = selectedItems.length - 1; i >= 0; i--) {
            int position = selectedItems[i];
            mData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {

        View mSelectedOverlay;
        TextView mNameView;
        TextView mDescriptionView;

        private ClickListener mClickListener;

        public ViewHolder(View itemView, ClickListener clickListener) {
            super(itemView);

            mSelectedOverlay = itemView.findViewById(R.id.selected_overlay);
            mNameView = (TextView) itemView.findViewById(R.id.name);
            mDescriptionView = (TextView) itemView.findViewById(R.id.description);

            mClickListener = clickListener;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) {
                mClickListener.onItemClicked(getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (mClickListener != null) {
                return mClickListener.onItemLongClicked(getAdapterPosition());
            }
            return false;
        }

        public interface ClickListener {
            void onItemClicked(int position);

            boolean onItemLongClicked(int position);
        }
    }
}
