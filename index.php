<?php
/**
* waioDev sk3LTn
 *
 */

?>
<?php
    require_once("header.php");
?>

</head>
<body>

    <header class="main-header claerfix">
        <?php
            require_once("header-container.php");
        ?>
    </header><!-- .main-header -->

    <div id="main">
        <div class="hero">

            <div class="container">
                <h1>I'm BibSys!</h1>
                <p>The simpliest .bib files manager. The section bellow describes what I can do.</p>
                <p>If you're already been here, use the search bar to find what you are looking for.</p>
                <div class="input-group input-group-lg">
                    <input type="text" class="form-control" placeholder="Type here..." aria-describedby="sizing-addon1">
                    <a class="input-group-addon search-button" href="#"><i class="glyphicon glyphicon-search"></i></a>
                </div>
            </div><!-- .container -->
        </div><!-- .hero -->
            
        <div class="wrapper-content">
            <div class="container">
                <div class="col-md-4">
                    <div class="card">
                        <h2 class="card-title">Format</h2>
                        <p class="card-text">Open a file .bib and apply patterns to format his content.</p>
                        <button class=" btn bt-default action-button">View details 
                            <i class="fa fa-angle-double-right"></i>
                        </button>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card">
                        <h2 class="card-title">Compare</h2>
                        <p class="card-text">Open two files and see their differences with highlight lines.</p>
                        <button class=" btn bt-default action-button">View details 
                            <i class="fa fa-angle-double-right"></i>
                        </button>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card">
                        <h2 class="card-title">Join</h2>
                        <p class="card-text">Open two .bib and join to append their informations, field by field.</p>
                        <button class=" btn bt-default action-button">View details 
                            <i class="fa fa-angle-double-right"></i>
                        </button>
                    </div>
                </div>
            </div><!-- .container -->
        </div><!-- .wrapper-content -->

    </div><!-- #main -->

<?php require_once("footer.php"); ?>