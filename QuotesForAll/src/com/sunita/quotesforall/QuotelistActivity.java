package com.sunita.quotesforall;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sunita.quotesforall.vo.QuoteVo;
import com.sunita.quotesforall.vo.QuotesVo;

public class QuotelistActivity extends FragmentActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
     * will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    Map<String, String> positionXml = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quotelist);

        loadPositionXML();
        
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the app.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }
    
    private void loadPositionXML() {
    	this.positionXml.put("1", "CharacterBuildingQuotations.xml");
    	this.positionXml.put("2", "CommunicationQuotations.xml");
    	this.positionXml.put("3", "DealingWithPeople.xml");
    	this.positionXml.put("4", "DestinyQuotations.xml");
    	this.positionXml.put("5", "ExperienceQuotations.xml");
    	this.positionXml.put("6", "FearOfFailure.xml");
    	this.positionXml.put("7", "FollowYourDreams.xml");
	}

    



	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quotelist, menu);
        return true;
    }
    
    

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a DummySectionFragment (defined as a static inner class
            // below) with the page number as its lone argument.
            Fragment fragment = new DummySectionFragment();
            Bundle args = new Bundle();
            int pos = position + 1;
            args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, pos);
            QuotesVo quotesVo;
			try {
				quotesVo = SAXXMLParser.parse(getAssets().open(positionXml.get("" + pos)));
				args.putParcelable("Name", quotesVo);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A dummy fragment representing a section of the app, but that simply
     * displays dummy text.
     */
    public static class DummySectionFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        public static final String ARG_SECTION_NUMBER = "section_number";
        

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        	int pos = getArguments().getInt(ARG_SECTION_NUMBER);
        	QuotesVo quotesVo = getArguments().getParcelable("Name");
        	
            View rootView = inflater.inflate(R.layout.fragment_quotelist_dummy, container, false);
            //TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
            //dummyTextView.setText(pos +" - "+quotesVo.getTopic());
            String[] str = new String[]{"A", "B"};
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.fragment_quotelist_dummy, str);
            ListView listview = (ListView) rootView.findViewById(R.id.quotelistview);
            listview.setAdapter(adapter);
            return rootView;
        }
    }

}
