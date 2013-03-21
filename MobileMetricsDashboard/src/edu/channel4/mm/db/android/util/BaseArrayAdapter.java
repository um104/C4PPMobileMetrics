package edu.channel4.mm.db.android.util;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public abstract class BaseArrayAdapter<T> extends ArrayAdapter<T> {

   private LayoutInflater inflater;
   private int cellLayoutId;

   public BaseArrayAdapter(Context context, int cellLayoutId, List<T> arrayList) {
      super(context, 1, arrayList);
      setLayoutInflater(context);
      setCellLayoutId(cellLayoutId);
   }

   public BaseArrayAdapter(Context context, int cellLayoutId, T[] arrayList) {
      super(context, 1, arrayList);
      setLayoutInflater(context);
      setCellLayoutId(cellLayoutId);
   }

   private void setLayoutInflater(Context context) {
      inflater = LayoutInflater.from(context);
   }

   private void setCellLayoutId(int cellLayoutId) {
      this.cellLayoutId = cellLayoutId;
   }

   @Override
   final public View getView(int position, View convertView, ViewGroup parent) {
      // Sanity check
      if (getCount() == 0) {
         Log.e("List is empty: will not draw cell");
         return convertView;
      }

      // If you can't reuse a cell, then inflate a new one.
      if (convertView == null) {
         convertView = inflater.inflate(cellLayoutId, null);
      }

      // Call the user's version of getViewEnhanced() on the resulting
      // convertView.
      // While you're at it, give him the actual object at the position given.
      return getViewEnhanced(getItem(position), convertView);
   }

   /**
    * @param object
    *           Automatically retrieved object for the array.
    * @param convertedView
    *           Automatically inflated (or reused) cell View
    * @return
    */
   public abstract View getViewEnhanced(T object, View convertedView);
}
