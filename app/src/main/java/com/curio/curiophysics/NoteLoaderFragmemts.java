package com.curio.curiophysics;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.curio.curiophysics.Adapters.NoteFragmentsAdapter;
import com.curio.curiophysics.Common.TappedSubChapterData;

public class NoteLoaderFragmemts extends AppCompatActivity {

    CustomViewPager subChaptersViewPager;
    int i;
    NoteFragmentsAdapter noteFragmentsAdapter;
    int tappedSubChapter;
    String currentChapterName;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                Fragment page = noteFragmentsAdapter.getItem(subChaptersViewPager.getCurrentItem());
                NoteFragment noteFragment = (NoteFragment) page;
                noteFragment.goToPrevCard();
                return true;
            case R.id.action_skip_next:
                subChaptersViewPager.setCurrentItem(subChaptersViewPager.getCurrentItem()+1);
                getSupportActionBar().setTitle(TappedSubChapterData.currentChapter.getSubChapters().get(subChaptersViewPager.getCurrentItem()).getName());
                return true;
            case R.id.action_skip_prev:
                subChaptersViewPager.setCurrentItem(subChaptersViewPager.getCurrentItem()-1);
                getSupportActionBar().setTitle(TappedSubChapterData.currentChapter.getSubChapters().get(subChaptersViewPager.getCurrentItem()).getName());
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_note_fragment_actions, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_loader_fragmemts);


        if (getIntent() != null) {
            tappedSubChapter = getIntent().getIntExtra("tappedSubChapter",0);
        }

        subChaptersViewPager = findViewById(R.id.subchapters_view_pager);
        subChaptersViewPager.disableScroll(true);
        setupViewPager(subChaptersViewPager,tappedSubChapter);

        //action bar
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(TappedSubChapterData.currentChapter.getSubChapters().get(subChaptersViewPager.getCurrentItem()).getName());

    }

    public void setupViewPager(ViewPager viewPager ,int tappedSubChapter) {
        noteFragmentsAdapter = new NoteFragmentsAdapter(getSupportFragmentManager());
        for (i = 0; i < TappedSubChapterData.subChaptersInChapter; i++) {
            currentChapterName = TappedSubChapterData.currentChapter.getSubChapters().get(i).getName();
            noteFragmentsAdapter.addFragment(NoteFragment.newInstance(TappedSubChapterData.currentChapter.getSubChapters().get(i).getSubChapterId(),
                    currentChapterName));
            viewPager.setAdapter(noteFragmentsAdapter);
            viewPager.setCurrentItem(tappedSubChapter);
        }
    }

}
