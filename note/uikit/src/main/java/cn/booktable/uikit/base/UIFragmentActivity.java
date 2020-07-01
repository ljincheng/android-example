package cn.booktable.uikit.base;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;
import java.util.function.Function;

public abstract class UIFragmentActivity extends AppCompatActivity {

    /**
     * fragmentContainerId
     * @return
     */
    public abstract int fragmentContainerId();

    private boolean _actionBarVisible_tag=true;
    public void setActionBarVisible(boolean visible)
    {
        _actionBarVisible_tag=visible;
        ActionBar ab = getSupportActionBar();
        if(ab!=null)
        {
            if(_actionBarVisible_tag)
            {
                ab.show();
            }else {
                ab.hide();
            }
        }
    }

    public UIFragment getUIFragment(String tagName)
    {
        FragmentManager mManager=  getSupportFragmentManager();
 Fragment fragment=mManager.findFragmentByTag(tagName);
        if(fragment!=null && fragment instanceof UIFragment) {
            return (UIFragment) fragment;
        }

        return null;
    }


    public int startUIFragment(UIFragment fragment)
    {
        FragmentManager mManager=  getSupportFragmentManager();
        FragmentTransaction ft = mManager.beginTransaction();
         return ft.replace(fragmentContainerId(), fragment, fragment.getTagName())
            .addToBackStack(fragment.getTagName())
            .commit();

    }

    @Override
    public void onBackPressed() {
        FragmentManager mManager= getSupportFragmentManager();
        List<Fragment> fragmentList= mManager.getFragments();
        if(mManager.getBackStackEntryCount()<1)
        {
            super.onBackPressed();
        }else{
            mManager.popBackStackImmediate();
        }

    }
}
