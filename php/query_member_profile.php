<?php
    include("connect.php");

    $member_id = $_GET['member_id'];
    // $event_name = $_GET['event_name'];
    // $event_detail= $_GET['event_detail'];


        $sql = "SELECT * FROM member WHERE member_id='$member_id'";
     
       
        $result = array();
        $query = mysqli_query($con,$sql);

        while($row = mysqli_fetch_assoc($query)){
        array_push($result,array("member_username" => $row["member_username"], 
                                "member_fname" => $row["member_fname"], 
                                "member_lname" => $row["member_lname"],
                                "member_profile" => $row["member_profile"],   
                                "member_phone" => $row["member_phone"]));
                               

    }

    print json_encode(array('result' => $result));

    mysqli_close($con);
    
    ?>


    