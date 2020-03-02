<?php
    include("connect.php");

    $event_member_id = $_POST['event_member_id'];
    $event_name = $_POST['event_name'];
    $event_detail= $_POST['event_detail'];
    $event_location = $_POST['event_location'];
    $event_type = $_POST['event_type'];
    $event_logo = $_POST['event_logo'];
    $event_org = $_POST['event_org'];
    $event_date = $_POST['event_date'];

    $event_lat_string = $_POST['event_lat'];
    $event_lnt_string = $_POST['event_lnt'];

    // $event_lat = floatval($event_lat_string);
    // $event_lnt = floatval($event_lnt_string);

    $before_parse = str_replace('/','-',$event_date);
    $parse = date('Y-m-d',strtotime($before_parse));
    $name = md5(uniqid(mt_rand(), true));
    $event_sum = $_POST['event_sum'];
   
    $ImagePath = "upload/$name.jpg";
    
        $sql ="INSERT INTO events (event_id,event_name,event_detail,event_location,event_type,event_org,event_date,event_member_id,event_logo,event_sum,event_lat,event_lng) 
        VALUES ('$event_id',
        '$event_name',
        '$event_detail',
        '$event_location',
        '$event_type',
        '$event_org',
        '$parse',
        '$event_member_id',
        '$ImagePath',
        '$event_sum',
        '$event_lat_string',
        '$event_lnt_string')";


if(mysqli_query($con,$sql)){
    file_put_contents($ImagePath,base64_decode($event_logo));
    echo "สำเร็จ";
}else{
    echo "ไม่";
}

    mysqli_close($con);
    
    ?>