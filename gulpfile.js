// Load plugins
var gulp            = require('gulp');
var browserSync     = require('browser-sync').create();
var sass            = require('gulp-sass');
var cleanCss        = require('gulp-clean-css');
var sourcemaps      = require('gulp-sourcemaps');
var autoprefixer    = require('gulp-autoprefixer');
var reload          = browserSync.reload;


// Default Paths
var path = {
    scss: 'assets/scss',
    css: 'assets/css' 
};


//BrowserSync
gulp.task('serve', ['sass'], function() {

    browserSync.init({
        proxy: 'bibsys.dev/',
        host: 'localhost',
        port: 3002,
        online: false,
        open: false
    });

    gulp.watch(path.scss + '/**/*.scss', ['sass']);
    gulp.watch('**/*.php').on('change', reload);
});


// Compile SASS & auto-inject into browsers
gulp.task('sass', function () {
    return gulp.src(path.scss + '/**/*.scss')
        .pipe(sourcemaps.init())
        .pipe(sass().on('error', sass.logError))
        .pipe(autoprefixer())
        .pipe(sourcemaps.write())
        .pipe(gulp.dest(path.css))
        .pipe(browserSync.stream());
});


// Minify CSS
gulp.task('minify-css', function() {
    gulp.src(path.css + '/style.css')
        .pipe(sourcemaps.init())
        .pipe(cleanCss({compatibility: 'ie8'}))
        .pipe(sourcemaps.write())
        .pipe(gulp.dest(path.css))
        .pipe(browserSync.stream());
});


// DEV Task
gulp.task('dev', ['serve']);

// DEPLOY Task
gulp.task('deploy', ['serve'], function () {
    gulp.watch(path.css + '/*.css', ['minify-css']);
});
