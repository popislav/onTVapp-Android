package at.sw2015.ontvapp.uitest;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.robotium.solo.Solo;

import java.util.Arrays;

import at.sw2015.ontvapp.MainActivity;
import at.sw2015.ontvapp.R;


public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo mySolo;

    MainActivity mainActivity;

    public MainActivityTest(){
        super(MainActivity.class);
    }


    public void setUp() throws Exception {
        super.setUp();
        mainActivity = getActivity();
        mySolo = new Solo(getInstrumentation(), getActivity());
    }

    public void testButtons(){
        mySolo.waitForActivity("MainActivity");
        mySolo.clickOnImageButton(0);
        mySolo.waitForActivity("AdditnalActivity");
    }

    public void testSpinner(){
        mySolo.waitForActivity("MainActivity");
        View view1 = mySolo.getView(Spinner.class, 0);
        mySolo.clickOnView(view1);
        mySolo.scrollToTop();
    }

    public void testNowAndNext(){
        mySolo.waitForActivity("MainActivity");
        Boolean nowExists = mySolo.searchText("Now:");
        mySolo.waitForActivity("MainActivity");
        Boolean nextExists = mySolo.searchText("Next:");
        assertTrue(nowExists && nextExists);
    }

    public void testChooseChannel(){
        mySolo.waitForActivity("MainActivity");
        Boolean chooseChannel = mySolo.searchText("choose channel");
        assertTrue(chooseChannel);
    }

    public void testTextResults(){
        mySolo.waitForActivity("MainActivity");
        Boolean nowTime = (Boolean) mySolo.getCurrentActivity().findViewById(R.id.now_time).isShown();
        Boolean nowContent = (Boolean) mySolo.getCurrentActivity().findViewById(R.id.now_content).isShown();
        Boolean nextTime = (Boolean) mySolo.getCurrentActivity().findViewById(R.id.next_time).isShown();
        Boolean nextContent = (Boolean) mySolo.getCurrentActivity().findViewById(R.id.next_content).isShown();
        assertTrue(nowTime && nowContent && nextTime && nextContent);
    }

    public void testGetLogo(){
        mySolo.waitForActivity("MainActivity");
        Boolean isVisible = (Boolean) mySolo.getCurrentActivity().findViewById(R.id.logo).isShown();
        assertTrue(isVisible);
        Boolean isVisible2 = (Boolean) mySolo.getCurrentActivity().getResources().getDrawable(R.drawable.orf1).isVisible();
        assertTrue(isVisible2);
    }

    public void testBothButtons(){
        mySolo.waitForActivity("MainActivity");
        mySolo.clickOnImageButton(0);
        mySolo.waitForActivity("AdditionalActivity");
        mySolo.clickOnImageButton(0);
        mySolo.waitForActivity("MainActivity");
        mySolo.sleep(3000);
    }

    public void testAdditionalTitle(){
        mySolo.waitForActivity("MainActivity");
        Spinner spinner = (Spinner) mySolo.getView(Spinner.class, 0);
        mySolo.pressSpinnerItem(0, 4);
        mySolo.clickOnImageButton(0);
        mySolo.waitForActivity("AdditionalActivity");
        TextView largeView = (TextView) mySolo.getView("textLarge");
        assertEquals("puls4", largeView.getText().toString());
    }

    public void testAdditionalTable(){
        mySolo.waitForActivity("MainActivity");
        mySolo.clickOnImageButton(0);
        mySolo.waitForActivity("AdditionalActivity");
        mySolo.sleep(3000);
        Boolean tableTime =  mySolo.getView("tableTime").isShown();
        Boolean tableContent = mySolo.getView("tableContent").isShown();
        assertTrue(tableTime && tableContent);
    }


    public void tearDown() throws Exception {
        //tearDown();
    }
}