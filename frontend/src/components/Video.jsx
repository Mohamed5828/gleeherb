import React from "react";
import ReactPlayer from "react-player";
const VIDEO_PATH =
  "https://firebasestorage.googleapis.com/v0/b/blogimgupload-3998a.appspot.com/o/Glee%2FBrown%20Modern%20Aesthetic%20Beauty%20Salon%20Presentation.mov?alt=media&token=9cded74c-0c67-41ad-b6a1-acfb48b19838";
function PlayerComponent() {
  return (
    <div className="video-player">
      <ReactPlayer
        url={VIDEO_PATH}
        loop="true"
        muted="true"
        playing="true"
        width={"90vw"}
        height={"auto"}
      />
    </div>
  );
}
export default PlayerComponent;
