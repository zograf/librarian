import axios from "axios"
import { useEffect, useState } from "react"
import { API } from "../../../enviroment"

export default function AdditionalSubjects({additionalSubjects}) {
    let path = '/user/preferences/'
    const token = localStorage.getItem("token")

    const [phrase, setPhrase] = useState("")
    const [foundKeywords, setFoundKeywords] = useState([])
    const [additionalKeywords, setAdditionalKeywords] = useState([...additionalSubjects])
    const [searchSent, setSearchSent] = useState(false)

    const handlePhrase = (e) => setPhrase(e.target.value)

    const searchKeywords = (e) => {
        if (phrase.length >= 3)
            axios.post(API + "/subjects/like", null, { params: { phrase : phrase }})
                .then(resp => {
                    setFoundKeywords(resp.data)
                    setSearchSent(true)
                })
        else if (phrase.length == 0) clearKeywords()
        else {
            setFoundKeywords([])
            setSearchSent(true)
        }
    }
    const clearKeywords = () => {
        setFoundKeywords([])
        setPhrase("")
        setSearchSent(false)
    }
    const addKeyword = (keyword) => {
        axios.put(API + path + "additional/" + keyword.id, null, { headers: {"Authorization" : `Bearer ${token}`} })
            .then(res => {
                console.log(res.data);
                setAdditionalKeywords((prevList) => {
                    return [...prevList, keyword]
                })
            })
            .catch(e => alert(e))
    }
    const removeKeyword = (keyword) => {
        axios.delete(API + path + "additional/" + keyword.id, { headers: {"Authorization" : `Bearer ${token}`} })
            .then(res => {
                console.log(res.data);
                setAdditionalKeywords((prevList) => {
                    return prevList.filter((x) => x.id !== keyword.id)
                })
            })
            .catch(e => alert(e))
    }

    return(
        <div>        
            {additionalKeywords.length > 0 && <div className="showing">
                <p className="section-title vi-spacer-xl hi-spacer-xs">Additional Keywords</p>
                <div className="flex center wrap gap-xs">{
                    additionalKeywords.map((keyword) => {return (
                        <button className="flex center showing" style={{paddingRight:'0px'}} disabled={true}>
                            <p>{keyword.keyword}</p>
                            <button className="icon-button"><span className="material-symbols-outlined icon" onClick={() => { removeKeyword(keyword) }}>cancel</span></button>
                        </button>
                )})
                }</div>
            </div>}

            <p className="card-body neutral v-spacer-xs vi-spacer-l hi-spacer-xs">Add more Keywords</p>
            <div className="flex v-spacer-xs gap-xs">
                <div className="input-wrapper regular-border v-spacer-xs" style={{paddingRight:'4px'}}>
                    <span className="material-symbols-outlined icon input-icon">search</span>
                    <input placeholder="Search" value={phrase} onChange={handlePhrase} onKeyUp={searchKeywords}/>
                    {searchSent && <button className="icon-button"><span className="material-symbols-outlined icon" onClick={clearKeywords}>cancel</span></button>}
                </div>
                {searchSent && 
                    <p className="flex center showing card-body" 
                        style={{
                            height:'50px', 
                            transition:'all 0.08s ease-in-out', 
                            backgroundColor: phrase.length < 3 ? 'rgb(var(--error))' : 'rgb(var(--accent))', 
                            color: phrase.length < 3 ? 'rgb(var(--on-error))' : 'rgb(var(--on-primary-dark))',
                            borderRadius:'var(--input-radius)',
                            padding:'0 16px'
                        }}>{phrase.length < 3 ? 'Phrase Too Short' : foundKeywords.length} Results
                    </p>
                }
            </div>
            
            <div className="flex center wrap gap-xs">{
                foundKeywords.map((keyword) => {
                        var found = additionalKeywords.find(x => x.id == keyword.id) != undefined
                        return (<button className={`flex center showing ${found ? 'text-button-selected' : 'outline-button'}`} onClick={() => { if(!found) addKeyword(keyword) }}>
                            {keyword.keyword} <p className="neutral hi-spacer-xs">{keyword.relevance}</p>
                        </button>)}
                    )
            }</div>
            
            {(!searchSent || (searchSent && phrase.length < 3)) && <div className="dashed-card showing flex column gap-xs center justify-center">
                <p className="card-title neutral">About Keywords</p>
                <p className="card-body">Each book has multiple keywords that describe it's content. Adding keywords to your favorites will fine tune your recommendations.</p>
            </div>}
            
            {searchSent && phrase.length >= 3 && foundKeywords.length == 0 && <div className="dashed-card showing flex column gap-xs center justify-center">
                <p className="card-title neutral">No Results</p>
                <p className="card-body">Try something different!</p>
            </div>}

        </div>   
    )

}