import React, { useState, useEffect } from "react";

import { Link } from "react-router-dom";

function ImageSlider(props) {
  const [currentImg, setCurrentImg] = useState(0);
  const length = props.slides.length;
  useEffect(() => {
    // Automatically advance every two seconds
    const intervalId = setInterval(() => {
      nextSlide();
    }, 4000);

    // Clear the interval when the component unmounts
    return () => clearInterval(intervalId);
  }, []);
  function nextSlide() {
    setCurrentImg((prevImg) =>
      prevImg === length - 1 ? (prevImg = 0) : (prevImg += 1)
    );
  }
  function prevSlide() {
    setCurrentImg((prevImg) =>
      prevImg === 0 ? (prevImg = length - 1) : (prevImg -= 1)
    );
  }
  if (!Array.isArray(props.slides) || props.slides.length <= 0) return null;
  return (
    <section className="slider-sec">
      {props.slides.map((slide, index) => {
        return (
          <div
            className={
              index === currentImg ? "slide fade active" : "slide fade"
            }
            key={index}
            onClick={() => {}}
          >
            <div className="s-image-container">
              {index === currentImg && (
                <img
                  src={
                    slide.image != ""
                      ? slide.image
                      : "https://firebasestorage.googleapis.com/v0/b/blogimgupload-3998a.appspot.com/o/nothumb.jpg?alt=media&token=39ca3696-b50c-444f-b3e1-786b4cb0533b"
                  }
                  className="img"
                />
              )}

              <a className="left-arrow" onClick={prevSlide}>
                &#10094;
              </a>
              <a className="right-arrow" onClick={nextSlide}>
                &#10095;
              </a>

              <div className="img-description">{slide.description}</div>
            </div>
          </div>
        );
      })}
    </section>
  );
}

export default ImageSlider;
