package com.xh189050936.weathercast;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.xh189050936.weathercast.bean.City;

public class CityActivity extends SingleFragmentActivity implements CityFragment.CallBacks,CityListFragment.CallBacks{

    private Fragment currentFragment;

    @Override
    protected Fragment createFragment() {
        currentFragment = CityFragment.newInstance();
        return currentFragment;
    }

    @Override
    public void onAddCity() {
        replaceFragment(CityListFragment.TAG);
    }

    @Override
    public void onClickCity(City city) {
        CityLab cityLab = CityLab.get(this);
        if(!cityLab.isExist(city)) {
            cityLab.addCity(city);
            Toast.makeText(this,R.string.add_city_success,Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,R.string.toast_city_conflict,Toast.LENGTH_SHORT).show();
        }
        replaceFragment(CityFragment.TAG);
    }

    public void replaceFragment(String tag){
        FragmentManager manager = getSupportFragmentManager();
        if(currentFragment != null){
            manager.beginTransaction().hide(currentFragment).commit();
        }
        currentFragment = manager.findFragmentByTag(tag);
        if(currentFragment == null){
            switch (tag){
                case CityFragment.TAG:
                    currentFragment = CityFragment.newInstance();
                    break;
                case CityListFragment.TAG:
                    currentFragment = CityListFragment.newInstance();
                    break;
            }
            manager.beginTransaction().add(R.id.fragment_container,currentFragment,tag).commit();
        }else{
            manager.beginTransaction().show(currentFragment).commit();
        }
    }
}
