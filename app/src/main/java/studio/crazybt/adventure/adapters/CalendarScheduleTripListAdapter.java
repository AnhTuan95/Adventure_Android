package studio.crazybt.adventure.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import studio.crazybt.adventure.R;

/**
 * Created by Brucelee Thanh on 30/09/2016.
 */

public class CalendarScheduleTripListAdapter extends RecyclerView.Adapter<CalendarScheduleTripListAdapter.ViewHolder> {

    private Context rootContext;

    public CalendarScheduleTripListAdapter(Context rootContext) {
        this.rootContext = rootContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calendar_schedule_trip, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvPeriodCalendar)
        TextView tvPeriodCalendar;
        @BindView(R.id.tvTitleCalendar)
        TextView tvTitleCalendar;
        @BindView(R.id.tvContentCalendar)
        TextView tvContentCalendar;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
