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
import android.util.SparseBooleanArray;
import android.view.View;

import java.util.Arrays;
import java.util.List;

public abstract class SelectableAdapter<S, T extends SelectableAdapter.ViewHolder> extends
        RecyclerView.Adapter<T> {

    protected ViewHolder.ClickListener mClickListener;
    private SparseBooleanArray mSelectedItems;
    private List<S> mData;

    public SelectableAdapter() {
        mSelectedItems = new SparseBooleanArray();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public List<S> getData() {
        return mData;
    }

    public void setData(List<S> data) {
        mData = data;
    }

    public boolean isSelected(int position) {
        return mSelectedItems.get(position);
    }

    public void toggleSelection(int position) {
        if (mSelectedItems.get(position, false)) {
            mSelectedItems.delete(position);
        } else {
            mSelectedItems.put(position, true);
        }
        notifyItemChanged(position);
    }

    public void clearSelection() {
        int[] selectedItems = getSelectedItems();
        mSelectedItems.clear();
        for (int i : selectedItems) {
            notifyItemChanged(i);
        }
    }

    public int getSelectedItemsCount() {
        return mSelectedItems.size();
    }

    public int[] getSelectedItems() {
        int selectedItemsCount = getSelectedItemsCount();
        int[] selectedItems = new int[selectedItemsCount];
        for (int i = 0; i < selectedItemsCount; i++) {
            selectedItems[i] = mSelectedItems.keyAt(i);
        }
        return selectedItems;
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

        private ClickListener mClickListener;

        public ViewHolder(View itemView, ClickListener clickListener) {
            super(itemView);
            mClickListener = clickListener;
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
