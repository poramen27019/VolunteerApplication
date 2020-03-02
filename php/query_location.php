<?php
    include("connect.php");

    if($_SERVER['REQUEST_METHOD'] == 'GET'){

        function distance($lat1, $lon1, $lat2, $lon2, $unit) {
            if (($lat1 == $lat2) && ($lon1 == $lon2)) {
              return 0;
            }
            else {
              $theta = $lon1 - $lon2;
              $dist = sin(deg2rad($lat1)) * sin(deg2rad($lat2)) +  cos(deg2rad($lat1)) * cos(deg2rad($lat2)) * cos(deg2rad($theta));
              $dist = acos($dist);
              $dist = rad2deg($dist);
              $miles = $dist * 60 * 1.1515;
              $unit = strtoupper($unit);
          
              if ($unit == "K") {
                return ($miles * 1.609344);
              } else if ($unit == "N") {
                return ($miles * 0.8684);
              } else {
                return $miles;
              }
            }
          }
        
        // $con = mysqli_connect(HOST,USER,PASS,DB) or die ('Unable t o connect');

        mysqli_set_charset($con,"utf8");

        // $latitude1 = $_GET["lat1"]; //cerrent location
        // $longitude1 = $_GET["lng1"]; //cerrent location
        
        $sql = "SELECT * FROM events";

        $query = mysqli_query($con,$sql);
        
        $result = array();
        
        while ($row = mysqli_fetch_array($query)){
          // $latitude2 = $row["event_lat"];
          // $longitude2 = $row["event_lng"];
          // $distance = distance($latitude1, $longitude1, $latitude2, $longitude2, "K");
          //     if($distance <= 5){
                array_push($result,array("event_id" => $row["event_id"], 
                                            "event_name" => $row["event_name"],
                                            "event_org" => $row["event_org"],
                                            "event_lat" => $row["event_lat"],
                                            "event_lng" => $row["event_lng"]));
                        }

        // }

       
        print json_encode(array('result' => $result));

        mysqli_close($con);
    }

    // $member_id = $_GET['member_id'];
    // $event_name = $_GET['event_name'];
    // $event_detail= $_GET['event_detail'];


    //     $sql = "SELECT * FROM events";
     
       
    //     $result = array();
    //     $query = mysqli_query($con,$sql);

    //     while($row = mysqli_fetch_assoc($query)){
    //     array_push($result,array("event_id" => $row["event_id"], 
    //                             "event_name" => $row["event_name"],
    //                             "event_org" => $row["event_org"],
    //                             "event_lat" => $row["event_lat"],
    //                             "event_lng" => $row["event_lng"]));
                               

    // }

    // print json_encode(array('result' => $result));

    // mysqli_close($con);
    
    ?>


    