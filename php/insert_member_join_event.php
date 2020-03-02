<?php
    include("connect.php");
    $event_id = $_POST['event_id'];
    $member_id = $_POST['member_id'];
    

   
    
    
        $sql ="INSERT INTO member_join_event (event_id,member_id) 
        VALUES ('$event_id','$member_id')";

        $result = mysqli_query($con,$sql);

    mysqli_close($con);
    
    ?>
<?php
                              $sql3 = mysqli_query($conn,"SELECT * from class_master INNER JOIN student_session_history
                              ON class_master.class_id = student_session_history.Class_id
                              WHERE student_id = '".$_SESSION['username']."'  ");
                              while($row3 = mysqli_fetch_array($sql3)){

                                $class_name_th = $row['class_name_th'];
                              }                       
                            

                             

                             ?>