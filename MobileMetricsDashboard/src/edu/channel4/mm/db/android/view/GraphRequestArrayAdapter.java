package edu.channel4.mm.db.android.view;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import edu.channel4.mm.db.android.R;
import edu.channel4.mm.db.android.model.request.GraphRequest;
import edu.channel4.mm.db.android.util.BaseArrayAdapter;

@SuppressLint("ViewConstructor")
public class GraphRequestArrayAdapter extends BaseArrayAdapter<GraphRequest> {

   public GraphRequestArrayAdapter(Context context, GraphRequest[] data) {
      super(context, R.layout.cell_graph_request, data);
   }

   public GraphRequestArrayAdapter(Context context, List<GraphRequest> data) {
      super(context, R.layout.cell_graph_request, data);
   }
   
   @Override
   public View getViewEnhanced(GraphRequest object, View convertedView) {
      // Reference the icon
      ImageView graphRequestIcon = (ImageView) convertedView
               .findViewById(R.id.imageViewGraphRequestIcon);
      graphRequestIcon.setImageDrawable(getContext().getResources()
               .getDrawable(object.getIconId()));

      // Reference the text view
      TextView graphRequestLabel = (TextView) convertedView
               .findViewById(R.id.textViewGraphRequestName);
      graphRequestLabel.setText(object.toString());

      return convertedView;
   }
}
