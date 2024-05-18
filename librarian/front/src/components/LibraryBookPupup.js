import { useState } from "react"
import { PopUpFrame, usePopup } from "./pop-up/PopUpFrame"
import "./LibraryBookPopup.css"

export default function LibraryBookPupup({book, token, popup}) {

    const [likedReadBook, setLikedReadBook] = useState(true)

    return(
        <PopUpFrame visible={popup.isVisible}>
                <div style={{
                    position: "relative"
                }}>
                    <div style={{
                        position: "absolute",
                        right: "0",
                        top: "0",
                        width: "33%",
                        height: "calc(100vh - 48px)"
                    }}>
                        <div className="main-content card">
                            <p>{book?.title}</p>
                            <button className="text-button" onClick={() => popup.hidePopup()}>Close</button>
                            <p className="card-title v-spacer-s"> Tell us did you liked the book?</p>
                            <div className="radio-inputs regular_border v-spacer-xs"> 
                                <label className="radio">
                                    <input type="radio" checked={likedReadBook} onClick={() => setLikedReadBook(true)}/>
                                    <span className="name">Like</span>
                                </label>
                                <label className="radio">
                                    <input type="radio" checked={!likedReadBook} onClick={() => setLikedReadBook(false)}/>
                                    <span className="name">Dislike</span>
                                </label>
                            </div>

                        </div>
                        <img className="hero-img" src={book?.cover}></img>
                    </div>
                </div>
            </PopUpFrame>
    )
}