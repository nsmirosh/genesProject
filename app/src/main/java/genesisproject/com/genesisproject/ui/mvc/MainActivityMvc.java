package genesisproject.com.genesisproject.ui.mvc;

import java.util.List;

import genesisproject.com.genesisproject.models.Repo;

public interface MainActivityMvc extends ViewMvc {

        interface InteractionListener {
            void onQueryTyped(String query);
            void onRepoClicked(Repo repo);
            void showPreviousResults();
            void onSearchClosed();
        }

        void setListener(InteractionListener listener);

        void bindData(List<Repo> repoList);
}
