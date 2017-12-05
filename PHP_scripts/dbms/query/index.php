<?php

require_once '../vendor/autoload.php';
require_once '../include/DbHandler.php';

$configuration = [
    'settings' => [
        'displayErrorDetails' => true,
    ],
];

$c = new \Slim\Container($configuration);
$app = new \Slim\App($c);

$app->post('/', function($req,$res,$args) {

    $response = array();
    $response['error'] = true;
    $response['message'] = "Something went wrong!";
    $db = new DbHandler();
    $query = $req->getParam("Query");
    $result = $db->customQuery($query);
    if($result != NULL)
        echoResponse(200,$result);
    else
        echoResponse(404,$response);
});

function echoResponse($status_code, $response) {

    // Http response code
    if($status_code == 200)
    echo json_encode($response);
    else
    echo "<center><h1 style='margin-top:50vh;'>Error 404 Not Found</h1><center>";
}

$app->run();

?>
