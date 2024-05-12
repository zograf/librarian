import axios from "axios"
import { useState } from "react"
import { API } from "../../enviroment"

// { headers: {"Authorization" : `Bearer ${token}`} }

export default function Preferences() {    
    const userId = localStorage.getItem("id")
    const token = localStorage.getItem("token")
    const email = localStorage.getItem("username")

    const [age, setAge] = useState(undefined)
    const [phrase, setPhrase] = useState("")
    const [foundKeywords, setFoundKeywords] = useState([])
    const [likedKeywords, setLikedKeywords] = useState([])
    const [searchSent, setSearchSent] = useState(false)

    const handleAge = (e) => setAge(e.target.value)
    const handlePhrase = (e) => setPhrase(e.target.value)

    const searchKeywords = (e) => {
        if (e.code != "Enter") return
        if (phrase.length < 3) return
        axios.post(API + "/subjects/like", null, { params: { phrase : phrase }})
            .then(resp => {
                setFoundKeywords(resp.data)
                setSearchSent(true)
            })
    }
    const clearKeywords = () => {
        setFoundKeywords([])
        setPhrase("")
        setSearchSent(false)
    }
    const addKeyword = (keyword) => {
        setLikedKeywords((prevList) => {
            return [...prevList, keyword]
        })
    }
    const removeKeyword = (keyword) => {
        setLikedKeywords((prevList) => {
            return prevList.filter((x) => x.id !== keyword.id)
        })
    }

    return(
        <div className="w-100 standard-padding">
        <p className="section-title">Main Information</p>

            <div className="flex">
                <div className="input-wrapper regular-border v-spacer-xs">
                    <span className="material-symbols-outlined icon input-icon">elderly</span>
                    <input placeholder="Age" type="number" min={5} value={age} onChange={handleAge}/>
                </div>
            </div>

            { likedKeywords.length > 0 && <div className="showing">
                <p className="section-title vi-spacer-xl">Favorite Keywords</p>
                <div className="flex center wrap gap-xs">{
                    likedKeywords.map((keyword) => {return (
                        <button className="flex center showing" style={{paddingRight:'0px'}} disabled={true}>
                            <p>{keyword.keyword}</p>
                            <button className="icon-button"><span className="material-symbols-outlined icon" onClick={() => { removeKeyword(keyword) }}>cancel</span></button>
                        </button>
                )})
                }</div>
            </div>}

            <p className="section-title vi-spacer-xl">Add Keywords</p>
            <div className="flex v-spacer-xs gap-xs">
                <div className="input-wrapper regular-border v-spacer-xs" style={{paddingRight:'4px'}}>
                    <span className="material-symbols-outlined icon input-icon">search</span>
                    <input placeholder="Search" value={phrase} onChange={handlePhrase} onKeyUp={searchKeywords}/>
                    {searchSent && <button className="icon-button"><span className="material-symbols-outlined icon" onClick={clearKeywords}>cancel</span></button>}
                </div>
                {searchSent && <p className="flex center showing card-body" style={{height:'50px', backgroundColor:'rgb(var(--accent))', color:'rgb(var(--on-primary-dark))', borderRadius:'var(--input-radius)', padding:'0 16px'}}>{foundKeywords.length} Results</p>}
            </div>
            <div className="flex center wrap gap-xs">{
                foundKeywords.map((keyword) => {
                        var found = likedKeywords.find(x => x.id == keyword.id) != undefined
                        return (<button className={`flex center showing ${found ? 'text-button-selected' : 'outline-button'}`} onClick={() => { if(!found) addKeyword(keyword) }}>
                            {keyword.keyword} <p className="neutral hi-spacer-xs">{keyword.relevance}</p>
                        </button>)}
                    )
            }</div>
            {!searchSent && <div className="dashed-card flex column gap-xs center justify-center">
                <p className="card-title neutral">About Keywords</p>
                <p className="card-body">Each book has multiple keywords that describe it's content. Adding keywords to your favorites will fine tune your recommendations.</p>
            </div>}
            {searchSent && phrase.length != 0 && foundKeywords.length == 0 && <div className="dashed-card flex column gap-xs center justify-center">
                <p className="card-title neutral">No Results</p>
                <p className="card-body">Try shortening the phrase or trying something different!</p>
            </div>}
        </div>
    )
}