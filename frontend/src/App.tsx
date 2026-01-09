// import { useState } from 'react'
import "./components/CurrencyList.tsx"
import CurrencyList from "./components/CurrencyList.tsx";

function App() {

  return (
    <>
        <div className={"bg-[#2A2A2A] h-screen"}>
        <header className={"flex justify-center items-center bg-gray-100"}>
        <h1 className={"text-4xl font-bold m-2 mr-100"}>Ka-Ching</h1>
        </header>
        <div className={"flex justify-center items-center"}>
            <form className={"w-[35%] h-100 ml-auto mr-auto mt-20 bg-[#cecece]"} action="">
                <h2 className={"text-2xl font-bold underline mb-10"} >Currency Converter</h2>
                <div>
                    <label htmlFor="sourceCurrency">Base Amount</label>
                    <input type="text"/>
                    <CurrencyList/>
                </div>
                <div>
                    <label htmlFor="convertedAmount">Converted amount</label>
                    <input type="text" className={"convertedAmount"}/>
                    <select>
                        <option value="EUR">EUR</option>
                        <option value="USD">USD</option>
                        <option value="AUD">AUD</option>
                    </select>
                </div>
            </form>
        </div>
            </div>
    </>
  )
}

export default App
