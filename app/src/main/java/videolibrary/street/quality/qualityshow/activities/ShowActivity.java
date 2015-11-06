package videolibrary.street.quality.qualityshow.activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import videolibrary.street.quality.qualityshow.QualityShowApplication;
import videolibrary.street.quality.qualityshow.R;
import videolibrary.street.quality.qualityshow.api.user.dao.Category;
import videolibrary.street.quality.qualityshow.api.user.dao.Film;
import videolibrary.street.quality.qualityshow.api.user.dao.Saison;
import videolibrary.street.quality.qualityshow.api.user.dao.Serie;
import videolibrary.street.quality.qualityshow.api.user.dao.User;
import videolibrary.street.quality.qualityshow.api.user.helpers.FilmHelper;
import videolibrary.street.quality.qualityshow.api.user.helpers.SaisonHelper;
import videolibrary.street.quality.qualityshow.api.user.helpers.SerieHelper;
import videolibrary.street.quality.qualityshow.api.user.listeners.CategoryListener;
import videolibrary.street.quality.qualityshow.api.user.listeners.FilmListener;
import videolibrary.street.quality.qualityshow.api.user.listeners.SaisonListener;
import videolibrary.street.quality.qualityshow.api.user.listeners.SerieListener;
import videolibrary.street.quality.qualityshow.fragments.ProfilFragment;
import videolibrary.street.quality.qualityshow.fragments.RecommandationsFragment;
import videolibrary.street.quality.qualityshow.fragments.SettingsFragment;
import videolibrary.street.quality.qualityshow.fragments.ShowFragment;
import videolibrary.street.quality.qualityshow.listeners.ClickListener;

/**
 * Created by Sacael on 04/11/2015.
 */
public class ShowActivity extends AppCompatActivity implements FilmListener,SerieListener,ClickListener {
    private Toolbar toolbar;
    private User user;
    private Serie serie;
    private Film film;
    private Boolean IsMovie;
    private ProfilFragment profilFragment;
    private RecommandationsFragment recommandationsFragment;
    private SettingsFragment settingsFragment;
    private ShowFragment showFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Intent intent=getIntent();
        IsMovie = intent.getBooleanExtra("isMovie",true);
        toolbar = (Toolbar) findViewById(R.id.show_toolbar);
        setSupportActionBar(toolbar);
        ImageView imagev=(ImageView)findViewById(R.id.show_image);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Object p;
        if(IsMovie)
        {
            film=(Film)intent.getParcelableExtra("show");
            getSupportActionBar().setTitle(film.getTitle());
            p = film.getPoster().get("thumb");
        }
        else{
            serie = (Serie)intent.getParcelableExtra("show");
            p = serie.getPoster().get("thumb");
            getSupportActionBar().setTitle(serie.getTitle());
        }


        String image = (String) p;
        if (image == null) {
            Drawable drawable = QualityShowApplication.getContext().getDrawable(R.drawable.undefined_poster);
            imagev.setImageDrawable(drawable);
        } else {
            Picasso.with(QualityShowApplication.getContext()).load(image).into(imagev);
        }
        user = QualityShowApplication.getUserHelper().getCurrentUser();
        if (user == null) {
            user = new User();
            user.setUsername("Anonyme");
        }
        showFragment = new ShowFragment();
        showFragment.setShow(intent.getParcelableExtra("show"));
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.show_frame_container, showFragment);
        transaction.commit();



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.show_menu, menu);

        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.add_watch:
                Toast.makeText(ShowActivity.this, "add", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void filmIsAdded(Film film) {

    }

    @Override
    public void filmIsDeleted() {

    }

    @Override
    public void getFilms(ArrayList<Film> films) {

    }

    @Override
    public void serieIsAdded(Serie serie) {

    }

    @Override
    public void serieIsDeleted() {

    }

    @Override
    public void getSeries(ArrayList<Serie> series) {

    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onItemClick(Object item) {

    }
}