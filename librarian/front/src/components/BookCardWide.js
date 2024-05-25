import axios from "axios";
import "./BookCardWide.css"
import { API } from "../enviroment";
import { useEffect, useState } from "react";

export default function BookCardWide({book, isInLibrary, isLibraryView, onClick, isRead, isLiked = false, onPrefUpdateCallback = () => { }}) {

    const token = localStorage.getItem("token")
    const [inLibrary, setInLibrary] = useState(isInLibrary)
    useEffect(() => setInLibrary(isInLibrary), [isInLibrary])

    const addToLib = (e) => {
        console.log(e);
        e.stopPropagation()
        if(inLibrary) {
            axios.delete(API + "/user/preferences/library/" + book.id, { headers: {"Authorization" : `Bearer ${token}`} })
            .then(res => {
                setInLibrary(false) 
                onPrefUpdateCallback(res.data) 
            })
            .catch(e => alert(e))
        }
        else {
            axios.put(API + "/user/preferences/library/" + book.id, null, { headers: {"Authorization" : `Bearer ${token}`} })
                .then(res => {
                    setInLibrary(true) 
                    onPrefUpdateCallback(res.data)
                })
                .catch(e => alert(e))
        }
    }
    return(
        <div
            className="card book-card-wide" 
            style={{
                width:'100%',
                aspectRatio:'14/9', 
                padding: '0',
                overflow: "hidden",
                position: "relative"
            }}
            onClick={onClick}
        >
            <div
                className="hero-img-wide"
                style={{
                    backgroundImage: "url(" + book.cover + "), url(https://static-01.daraz.pk/p/9c4bbb21ac32475a2f3d8c55d2b7337d.jpg_750x750.jpg_.webp)",
                    backgroundSize: "cover",
                    backgroundPosition: "left",
                    backgroundRepeat: "no-repeat",
                }}>
            </div>

            <p className="book-category-wide shadow">{book.category.keyword}</p>

            {isLibraryView && isRead && <div className={`floating-icon-circle-wide shadow flex center justify-center ${isLiked ? 'liked-icon-wide' : 'disliked-icon-wide'}`}>
                <span className="material-symbols-outlined icon" style={{scale:'1.05'}}>{isLiked ? 'thumb_up' : 'thumb_down'}</span>
            </div>}

            {!isRead && <button className="solid-icon-button shadow save-button-wide" onClick={addToLib}>
                <span className="material-symbols-outlined icon">{inLibrary ? 'label_off' : 'new_label'}</span>
            </button>}

            <div className= {`flex space-between column standard-padding-xxs book-content-wrapper-wide ${(isLibraryView && !inLibrary) || book?.newToTrending ? 'taller-book-content-wide' : ''}`}>
                <div className="book-content-inner-wide">
                    <p className="book-title-wide">{book.title}</p>
                    <div className="data-grid v-spacer-m">
                        <p className="key-item" style={{paddingRight:'0.25em'}}>By</p>
                        <p className="value-item">{book.authors[0].name}</p>
                    </div>
                    <div className="flex wrap gap-xxs">
                        {book.subjects.map(subject => {return(<p className="subject-chip-wide">{subject.keyword}</p>)})}
                    </div>
                </div>
            </div>
        </div>
    )
}