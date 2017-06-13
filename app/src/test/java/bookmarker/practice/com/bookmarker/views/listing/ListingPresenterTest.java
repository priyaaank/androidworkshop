package bookmarker.practice.com.bookmarker.views.listing;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import bookmarker.practice.com.bookmarker.services.ManagedUrlService;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ListingPresenterTest {

    private ListingPresenter listingPresenter;

    @Mock
    private ListingView listingView;

    @Mock
    private ManagedUrlService managedUrlService;

    @Before
    public void setUp() {
        this.listingPresenter = new ListingPresenter(listingView, managedUrlService);
    }

    @Test
    public void testThatAdapterIsSetOnActivityCreate() {
        this.listingPresenter.onActivityCreate();

        verify(listingView).setupAdapter();
    }

    @Test
    public void testThatLoadingViewIsShownWhenActivityIsFirstLoaded() throws Exception {
        this.listingPresenter.onActivityCreate();

        verify(listingView).showLoadingView();
    }

}
