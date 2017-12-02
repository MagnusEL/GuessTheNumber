package com.larsen.magnus.guessthenumber;

/**
 * Created by magnus on 30.05.16.
 */
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;

/**
 * Created by Ivan Rust on 28.05.2016.
 */
class HallAdapter extends RecyclerView.Adapter<HallAdapter.ViewHolder> {

    private String[] captions;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    @Override
    public HallAdapter.ViewHolder onCreateViewHolder (ViewGroup parent,int viewType){
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hall_card_layout,parent,false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position) {
        CardView cardView = holder.cardView;
        TextView textView = (TextView) cardView.findViewById(R.id.info_text);
        textView.setText(captions[position]);
    }

    public HallAdapter(String[] captions) {
        this.captions=captions;
    }

    @Override
    public int getItemCount() {
        return captions.length;
    }
}
