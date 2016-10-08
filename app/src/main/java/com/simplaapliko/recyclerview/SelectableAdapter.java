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

public abstract class SelectableAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    private SparseBooleanArray mSelectedItems;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public SelectableAdapter() {
        mSelectedItems = new SparseBooleanArray();
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
}
