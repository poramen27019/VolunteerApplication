<?php
    include("connect.php");

    // $member_id = $_GET['member_id'];
    // $event_name = $_GET['event_name'];
    // $event_detail= $_GET['event_detail'];


        // $sql = "SELECT * FROM events";
        $sql = " SELECT * FROM events 
        INNER JOIN member ON events.event_member_id = member.member_id";
        $result = array();
        $query = mysqli_query($con,$sql);

        while($row = mysqli_fetch_assoc($query)){
            $query2 = mysqli_query($con, 'SELECT COUNT(*) as num FROM member_join_event where event_id = ' . $row["event_id"]);
            $row2 = mysqli_fetch_assoc($query2);
            $count = $row2["num"];
        array_push($result,array("event_id" => $row["event_id"],
                                "event_member_id" => $row["event_member_id"],
                                "event_name" => $row["event_name"],
                                "event_location" => $row["event_location"], 
                                "event_date_close" => $row["event_date_close"],
                                "event_type" => $row["event_type"],
                                "event_org" => $row["event_org"],
                                "event_detail" => $row["event_detail"],
                                "member_fname" => $row["member_fname"],
                                "member_phone" => $row["member_phone"],
                                "event_logo" => $row["event_logo"],
                                "event_sum" => $row["event_sum"],
                                "num" => $count 
                            ));
                               

    }

    print json_encode(array('result' => $result));

    mysqli_close($con);
    
    ?>




    