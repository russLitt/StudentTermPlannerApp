package com.example.termplannerapp.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termplannerapp.R;
import com.example.termplannerapp.TermEditorActivity;
import com.example.termplannerapp.database.TermEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.termplannerapp.utilities.Constants.TERM_ID_KEY;

public class TermsAdapter extends RecyclerView.Adapter<TermsAdapter.ViewHolder> {

    private final List<TermEntity> mTerms;
    private final Context mContext;
    private OnTermListener mOnTermListener;

    public TermsAdapter(List<TermEntity> mTerms, Context mContext, OnTermListener onTermListener) {
        this.mTerms = mTerms;
        this.mContext = mContext;
        this.mOnTermListener = onTermListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.term_list_item, parent, false);
        return new ViewHolder(view, mOnTermListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final TermEntity term = mTerms.get(position);
        holder.mTextView.setText(term.getTermTitle());
        holder.mStartDate.setText(term.getTermStartDate());
        holder.mEndDate.setText(term.getTermEndDate());

        holder.mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TermEditorActivity.class);
                intent.putExtra(TERM_ID_KEY, term.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTerms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.term_text)
        TextView mTextView;
        @BindView(R.id.term_start)
        TextView mStartDate;
        @BindView(R.id.term_end)
        TextView mEndDate;
        @BindView(R.id.fab)
        FloatingActionButton mFab;

        OnTermListener onTermListener;

        public ViewHolder(@NonNull View itemView, OnTermListener onTermListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.onTermListener = onTermListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onTermListener.onTermClicked(getAdapterPosition());
        }
    }
    public interface OnTermListener {
        void onTermClicked(int position);
    }
}