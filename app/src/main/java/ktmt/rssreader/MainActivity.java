package ktmt.rssreader;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import ktmt.rssreader.Data.DataManager;
import ktmt.rssreader.Data.LocalData;
import ktmt.rssreader.Data.NewsItem;
import ktmt.rssreader.Data.RSS;
import ktmt.rssreader.adapters.HomePagerAdapter;
import ktmt.rssreader.fragments.BaseFragment;
import ktmt.rssreader.fragments.MainFragment;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DataManager.dbInit(this);
        super.onCreate(savedInstanceState);
//        RSS receive = new RSS();
//        receive.getRSS(0, 0);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        LocalData.initLocalData();
        DataManager.getData(this);
        changeFragment(MainFragment.newInstance());
    }

    public void changeFragment(BaseFragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment,null)
                .addToBackStack(null).commit();
    }

}



