package edu.channel4.mm.db.android.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import edu.channel4.mm.db.android.R;
import edu.channel4.mm.db.android.model.request.GraphRequest;

public class GraphRequestArrayAdapter extends ArrayAdapter<GraphRequest> {

	private GraphRequest[] data;

	public GraphRequestArrayAdapter(Context context, GraphRequest[] data) {
		super(context, R.layout.cell_graph_request, data);
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		GraphRequest graphRequest = data[position];
		ImageView graphRequestIcon;
		TextView graphRequestLabel;

		// If you couldn't recycle a cell, then inflate one.
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(R.layout.cell_graph_request, parent,
					false);
		}

		// Reference the icon
		graphRequestIcon = (ImageView) convertView
				.findViewById(R.id.imageViewGraphRequestIcon);

		// Reference the text view
		graphRequestLabel = (TextView) convertView
				.findViewById(R.id.textViewGraphRequestName);
		graphRequestLabel.setText(graphRequest.toString());

		return convertView;
	}
}
