package genesisproject.com.genesisproject.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import genesisproject.com.genesisproject.R;
import genesisproject.com.genesisproject.models.Repo;

/**
 * Created by nsmirosh on 6/17/17.
 */

public class RepoAdapter  extends RecyclerView.Adapter<RepoAdapter.ViewHolder> {

    private List<Repo> mDataset = new ArrayList<>();

    onInteractionListener mListener;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name_tv)
        TextView mNameTV;

        @BindView(R.id.container_ll)
        LinearLayout mContainerLL;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public void setData(List<Repo> repos) {
        mDataset.clear();
        mDataset.addAll(repos);
        notifyDataSetChanged();
    }

    public void setListener(onInteractionListener listener) {
        mListener = listener;
    }

    public List<Repo> getDataSet() {
        return mDataset;
    }


    @Override
    public RepoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_repo_data, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Repo repo = mDataset.get(position);
        String name = repo.getName();

        if (name.length() > 30) {
            name = repo.getName().substring(0, 30);
        }

        holder.mNameTV.setText(name);

        holder.mContainerLL.setOnClickListener(v -> {
            mListener.onRepoClicked(repo);
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface onInteractionListener {

        void onRepoClicked(Repo repo);
    }

}
