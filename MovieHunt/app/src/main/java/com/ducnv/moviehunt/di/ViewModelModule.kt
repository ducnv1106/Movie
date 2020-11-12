package com.ducnv.moviehunt.di

import com.ducnv.moviehunt.ui.favorite.FavoriteViewModel
import com.ducnv.moviehunt.ui.home.like.LikeViewModel
import com.ducnv.moviehunt.ui.home.HomeViewModel
import com.ducnv.moviehunt.ui.home.movie.MovieViewModel
import com.ducnv.moviehunt.ui.home.tv.TvViewModel
import com.ducnv.moviehunt.ui.image.ImagePagerViewModel
import com.ducnv.moviehunt.ui.image.ImageViewModel
import com.ducnv.moviehunt.ui.image.girdimagecast.GridImageCastViewModel
import com.ducnv.moviehunt.ui.login.LoginViewModel
import com.ducnv.moviehunt.ui.moviedetail.remote.MovieDetailViewModel
import com.ducnv.moviehunt.ui.movieviewall.nowplay.MovieNowPlayViewModel
import com.ducnv.moviehunt.ui.movieviewall.popular.MoviePopularViewModel
import com.ducnv.moviehunt.ui.movieviewall.toprated.MovieTopRatedViewModel
import com.ducnv.moviehunt.ui.movieviewall.upcoming.MovieUpcomingViewModel
import com.ducnv.moviehunt.ui.home.profile.ProfileViewModel
import com.ducnv.moviehunt.ui.moviedetail.bottomsheet.IntroductionBottomSheetViewMode
import com.ducnv.moviehunt.ui.moviedetail.dialogfragment.favorite.FavoriteMovieDialogViewModel
import com.ducnv.moviehunt.ui.moviedetail.dialogfragment.like.LikeMovieDialogViewModel
import com.ducnv.moviehunt.ui.rating.RatingViewModel
import com.ducnv.moviehunt.ui.splash.SplashViewModel
import com.ducnv.moviehunt.ui.walkthrough.WalkThroughViewModel
import com.ducnv.moviehunt.ui.walkthrough.walkthrough1.WalkThrough1ViewModel
import com.ducnv.moviehunt.ui.walkthrough.walkthrough2.WalkThrough2ViewModel
import com.ducnv.moviehunt.ui.walkthrough.walkthrough3.WalkThrough3ViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel() }
    viewModel { SplashViewModel() }
    viewModel { MovieViewModel(get(),get()) }
    viewModel { TvViewModel() }
    viewModel { WalkThroughViewModel(get()) }
    viewModel { WalkThrough1ViewModel() }
    viewModel { WalkThrough2ViewModel() }
    viewModel { WalkThrough3ViewModel() }
    viewModel {
        MovieDetailViewModel(
            get(),
            get()
        )
    }
    viewModel { MovieNowPlayViewModel(get()) }
    viewModel { MoviePopularViewModel(get()) }
    viewModel { MovieTopRatedViewModel(get()) }
    viewModel { MovieUpcomingViewModel(get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { ProfileViewModel() }
    viewModel { LikeViewModel(get()) }
    viewModel { FavoriteViewModel() }
    viewModel { RatingViewModel() }
    viewModel { ImagePagerViewModel() }
    viewModel { ImageViewModel() }
    viewModel { GridImageCastViewModel()}
    viewModel { IntroductionBottomSheetViewMode() }
    viewModel { LikeMovieDialogViewModel() }
    viewModel { FavoriteMovieDialogViewModel() }
}
