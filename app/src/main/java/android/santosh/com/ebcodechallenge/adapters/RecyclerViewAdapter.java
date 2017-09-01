package android.santosh.com.ebcodechallenge.adapters;

import android.content.Context;
import android.location.Address;
import android.santosh.com.ebcodechallenge.R;
import android.santosh.com.ebcodechallenge.model.EarthQuake;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Santosh on 9/1/17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static String TAG = RecyclerViewAdapter.class.getSimpleName();
    private Context context;
    private List<EarthQuake> earthQuakeList = new ArrayList<>();

    public RecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        return new RecyclerViewItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof RecyclerViewItemViewHolder) {
            RecyclerViewItemViewHolder recyclerViewItemViewHolder = (RecyclerViewItemViewHolder) viewHolder;
            EarthQuake earthQuake = earthQuakeList.get(position);
            if (earthQuake.getMagnitude() < 8.0f) {
                recyclerViewItemViewHolder.rootView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.white));
            } else {
                recyclerViewItemViewHolder.rootView.setBackgroundColor(ContextCompat.getColor(context, R.color.light_red));
            }
            if (earthQuake.getAddresses() != null && earthQuake.getAddresses().size() > 0) {
                Address address = earthQuake.getAddresses().get(0);
                recyclerViewItemViewHolder.locationTextView.setText(address.getCountryName());
            } else {
                recyclerViewItemViewHolder.locationTextView.setText("Unknown");
            }
            recyclerViewItemViewHolder.latitudeTextView.setText(String.format(Locale.US, "%s", earthQuake.getLatitude()));
            recyclerViewItemViewHolder.longitudeTextView.setText(String.format(Locale.US, "%s,", earthQuake.getLongitude()));
            recyclerViewItemViewHolder.magnitudeTextView.setText(String.format(Locale.US, "%s", earthQuake.getMagnitude()));
        }
    }

    @Override
    public int getItemCount() {
        return earthQuakeList.size();
    }

    public class RecyclerViewItemViewHolder extends RecyclerView.ViewHolder {
        View rootView;
        TextView locationTextView;
        TextView latitudeTextView;
        TextView longitudeTextView;
        TextView magnitudeTextView;

        public RecyclerViewItemViewHolder(View itemView) {
            super(itemView);
            rootView = itemView.findViewById(R.id.root_layout);
            locationTextView = itemView.findViewById(R.id.location_text_view);
            latitudeTextView = itemView.findViewById(R.id.latitude_text_view);
            longitudeTextView = itemView.findViewById(R.id.longitude_text_view);
            magnitudeTextView = itemView.findViewById(R.id.magnitude_text_view);
        }
    }

    public void swapList(List<EarthQuake> earthQuakeList) {
        this.earthQuakeList.clear();
        this.earthQuakeList.addAll(earthQuakeList);
        notifyDataSetChanged();
    }
}
