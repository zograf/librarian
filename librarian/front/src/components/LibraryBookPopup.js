import { useEffect, useState } from "react"
import { PopUpFrame, usePopup } from "./pop-up/PopUpFrame"
import "./LibraryBookPopup.css"
import axios from "axios"
import { API } from "../enviroment"

export default function LibraryBookPupup({book, token, popup, isLibraryView, isInLibrary, isRead = false, onPrefUpdateCallback = () => { }}) {

    const [likedReadBook, setLikedReadBook] = useState(true)
    const [selected, setSelected] = useState([])
    const onSelectionChanged = (subject) => {
        setSelected(prev => {
            let found = prev.find(item => { return item.id == subject.id })
            if (found == undefined) return [...prev, subject]
            else return prev.filter((x) => x.id !== subject.id)
        })
    }
    const [read, setRead] = useState(false)
    const [inLibrary, setInLibrary] = useState(false)
    useEffect(() => setInLibrary(isInLibrary), [isInLibrary])

    useEffect(() => reset(), [book])
    useEffect(() => {
        if(!read) reset()
    }, [read])

    const reset = () => {
        setSelected([])
        setLikedReadBook(true)
        setRead(false)
    }

    const submit = () => {
        console.log({
            "id" : book.id,
            "liked" : likedReadBook,
            "subjects" : selected
        });
        axios.post(API + "/user/preferences/book/read", {
            "id" : book.id,
            "liked" : likedReadBook,
            "subjects" : selected
        }, { headers: {"Authorization" : `Bearer ${token}`} })
        .then(res => {
            console.log(res)
            popup.hidePopup()
            onPrefUpdateCallback(res.data)
        })
    }

    const addToLib = (e) => {
        console.log(e);
        e.stopPropagation()
        if(isInLibrary) {
            axios.delete(API + "/user/preferences/library/" + book.id, { headers: {"Authorization" : `Bearer ${token}`} })
            .then(res => onPrefUpdateCallback(res.data) )
            .catch(e => alert(e))
        }
        else {
            axios.put(API + "/user/preferences/library/" + book.id, null, { headers: {"Authorization" : `Bearer ${token}`} })
                .then(res => onPrefUpdateCallback(res.data))
                .catch(e => alert(e))
        }
    }

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
                        <div className="main-content silent-scroll flex column space-between">
                            <div>
                                <div className="flex space-between gap-l v-spacer-xl">
                                    <p className="hero-book-title">{book?.title}</p>
                                    <button className="solid-icon-button" onClick={() => popup.hidePopup()}>
                                        <span className="material-symbols-outlined icon">close</span>
                                    </button>
                                </div>
                                <div className="data-grid v-spacer-m" style={{gap:"8px"}}>
                                    <p className="key-item">By:</p>
                                    <p className="value-item">{book?.authors[0].name}</p>
                                    <p className="key-item">Category: &nbsp;</p>
                                    <p className="value-item" style={{textTransform:'capitalize'}}>{book?.category.keyword}</p>
                                    {book?.firstPublishedYear != -1 && <p className="key-item">Published Year:</p>}
                                    {book?.firstPublishedYear != -1 && <p className="value-item">{book?.firstPublishedYear}</p>}
                                    {book?.description != '' && <p className="key-item">Description:</p>}
                                    {book?.description != '' && <p className="value-item">{book?.description}</p>}
                                    {book?.firstSentence != '' && <p className="key-item">First Sentence:</p>}
                                    {book?.firstSentence != '' && <p className="value-item">{book?.firstSentence}</p>}
                                    {!read && <p className="key-item vi-spacer-m">Keywords:</p>}
                                    {!read && <div className="flex wrap gap-xxs vi-spacer-m showing">
                                        {book?.subjects.map(subject => {return(<p className="subject-chip">{subject.keyword}</p>)})}
                                    </div>}
                                </div>
                            </div>
                    
                            {isLibraryView && !isRead && !read && <div className="showing-top-slide">
                                <button className="solid-accent-button w-100 flex center justify-center gap-xs" onClick={() => setRead(true) }>
                                    <span className="material-symbols-outlined icon">book_2</span>
                                    Mark As Read
                                </button>
                            </div>}

                            {!isLibraryView && <div className="showing-top-slide">
                                <button className="solid-accent-button w-100 flex center justify-center gap-xs" onClick={addToLib}>
                                    <span className="material-symbols-outlined icon">{inLibrary ? 'label_off' : 'new_label'}</span>
                                    {inLibrary ? 'Remove From Library' : 'Add To Library'}
                                </button>
                            </div>}

                            {isLibraryView && !isRead && read && <div className="showing-bottom-slide" style={{position:'absolute', bottom:'0'}}>
                                <div className="card v-spacer-s">
                                    <div className="radio-inputs regular_border v-spacer-xl"> 
                                        <label className="radio">
                                            <input type="radio" checked={likedReadBook} onClick={() => setLikedReadBook(true)}/>
                                            <span className="name">I Liked the Book</span>
                                        </label>
                                        <label className="radio">
                                            <input type="radio" checked={!likedReadBook} onClick={() => setLikedReadBook(false)}/>
                                            <span className="name">I Disliked the Book</span>
                                        </label>
                                    </div>
                                    <div className="flex center space-between gap-m v-spacer-m">
                                        <p className="card-title hi-spacer-xxs">{likedReadBook ? 'Select most liked subjects:' : 'Select most disliked subjects:'}</p>
                                        <p className="card-title bold h-spacer-s">{selected.length}/3</p>
                                    </div>
                                    <div className="flex wrap gap-xs">
                                        {book?.subjects.map(subject => {return(
                                            <button 
                                                className={`${(selected.length >= 3 && selected.indexOf(subject) == -1) ? 'disabled-outline-button' : selected.indexOf(subject) == -1 ? 'outline-button' : 'text-button-selected'}`} 
                                                onClick={() => {onSelectionChanged(subject)}}
                                                disabled={selected.length >= 3 && selected.indexOf(subject) == -1}
                                            >{subject.keyword}</button>)})}
                                    </div>
                                </div>
                                <div className="flex justify-end gap-xs">
                                    <button className="outline-icon-button icon-button" onClick={() => setRead(false)}>
                                        <span className="material-symbols-outlined icon">chevron_left</span>
                                    </button>
                                    <button className="solid-accent-button" onClick={submit}>Submit Preferences</button>
                                </div>
                            </div>}

                        </div>
                        <img className="hero-img" src={book?.cover}></img>
                    </div>
                </div>
            </PopUpFrame>
    )
}