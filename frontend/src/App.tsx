import { useEffect, useState } from "react";
import CurrencyList from "./components/CurrencyList";
import type { Currencies, Currency } from "./types.tsx";

function App() {
  const [darkMode, setDarkMode] = useState(false);

  const [fromCurrency, setFromCurrency] = useState<Currency>({
    isoCode: "EUR",
    name: "Euro",
    rate: 1,
    updatedAt: "",
  });
  const [toCurrency, setToCurrency] = useState<Currency>({
    isoCode: "EUR",
    name: "EUR",
    rate: 1,
    updatedAt: "",
  });

  const [amount, setAmount] = useState<number>(0);
  const [error, setError] = useState<string>("");
  const [currencies, setCurrencies] = useState<Currencies[]>([]);

  useEffect(() => {
    async function fetchCurrencies() {
      const res = await fetch("http://localhost:8080/api/rates");
      const data = await res.json();
      setCurrencies(data);
    }
    fetchCurrencies();
  }, []);

  const resetError = () => error && setError("");

  const calculateAmount = (amount: number, to: number, from: number) =>
    (amount * (to / from)).toFixed(4);

  function swapCurrencies() {
    setFromCurrency(toCurrency);
    setToCurrency(fromCurrency);
  }

  function toggleDarkMode() {
    setDarkMode(!darkMode);
  }

  return (
    <div
      className={`min-h-screen transition-colors duration-300
            ${darkMode ? "bg-[#1E1B2E]" : "bg-[#EDE9FE]"}`}
    >
      <header
        className={`${darkMode ? "bg-[#2A2544]" : "bg-[#DDD6FE]"} px-6 py-3 shadow`}
      >
        <div
          className={`flex justify-between items-center
                 max-w-150 mx-auto`}
        >
          <h1
            className={`text-3xl sm:text-4xl font-bold
                    ${darkMode ? "text-[#DDD6FE]" : "text-[#4C1D95]"}`}
          >
            Ka-Ching
          </h1>

          <button
            onClick={toggleDarkMode}
            className={`relative inline-flex h-8 w-16 items-center rounded-full transition-colors duration-300
    ${darkMode ? "bg-[#7C3AED]" : "bg-[#E5E7EB]"}`}
          >
            <span
              className={`inline-flex h-6 w-6 transform items-center justify-center rounded-full bg-white shadow-md transition-all duration-300
      ${darkMode ? "translate-x-9" : "translate-x-1"}`}
            >
              <span className="text-xs">{darkMode ? "🌚" : "🌞"}</span>
            </span>
          </button>
        </div>
      </header>

      {/*Converter*/}
      <div className="flex justify-center">
        <form
          className={`w-full sm:w-[90%] md:w-[60%] lg:w-[35%] mt-8 pt-4 pb-8 px-8 rounded-3xl shadow-lg
                    ${darkMode ? "bg-[#2A2544]" : "bg-[#F5F3FF]"}`}
        >
          <h2
            className={`sm:text-2xl text-center mb-10
                        ${darkMode ? "text-[#DDD6FE]" : "text-[#5B21B6]"}`}
          >
            Currency Converter
          </h2>

          {/*Base Amount*/}
          <label
            className={`mb-1 block text-sm ${darkMode ? "text-[#C4B5FD]" : "text-[#4C1D95]"}`}
          >
            Amount
          </label>

          <div className="grid grid-cols-[1fr_auto] gap-2 items-center">
            <input
              type="text"
              className={`rounded-md w-full pl-2 py-2 outline-none
                            ${
                              darkMode
                                ? "bg-[#1E1B2E] text-white focus:ring-[#7C3AED]"
                                : "bg-white focus:ring-[#C4B5FD]"
                            }`}
              placeholder="Enter amount..."
              onChange={(e) => {
                if (e.target.value.match(/^[0-9]+(\.[0-9]+)?$/)) {
                  resetError();
                  setAmount(parseFloat(e.target.value));
                } else {
                  setError("Enter a valid number...");
                  setAmount(0);
                }
              }}
            />

            <CurrencyList
              value={fromCurrency.isoCode}
              currencies={currencies}
              onChange={setFromCurrency}
              compact
              darkMode={darkMode}
            />
          </div>

          {/*Swap Button*/}
          <div className="flex justify-center my-6">
            <button
              type="button"
              onClick={swapCurrencies}
              className="h-12 w-12 bg-[#7C3AED] hover:bg-[#6D28D9] text-white rounded-full text-xl active:scale-95 transition"
            >
              ⇄
            </button>
          </div>

          {/*Converted Amount*/}
          <label
            className={`mb-1 block text-sm ${darkMode ? "text-[#C4B5FD]" : "text-[#4C1D95]"}`}
          >
            Converted Amount
          </label>

          <div className="grid grid-cols-[1fr_auto] gap-2 items-center">
            <input
              type="text"
              readOnly
              className={`rounded-md w-full pl-2 py-2 outline-none
                            ${darkMode ? "bg-[#1E1B2E] text-white" : "bg-white"}`}
              value={
                error === ""
                  ? calculateAmount(amount, toCurrency.rate, fromCurrency.rate)
                  : error
              }
            />

            <CurrencyList
              value={toCurrency.isoCode}
              currencies={currencies}
              onChange={setToCurrency}
              compact
              darkMode={darkMode}
            />
          </div>

          {/*Result*/}
          <div className="flex justify-center mt-6">
            <input
              type="text"
              readOnly
              className={`rounded-md w-full pl-2 py-2 text-center outline-none
                            ${darkMode ? "bg-[#1E1B2E] text-[#DDD6FE]" : "bg-white"}`}
              value={`1 ${fromCurrency.isoCode} = ${calculateAmount(
                1,
                toCurrency.rate,
                fromCurrency.rate,
              )} ${toCurrency.isoCode}`}
            />
          </div>
        </form>
      </div>

      {/*Currencies*/}
      <div className="flex justify-center">
        <div
          className={`w-full sm:w-[90%] md:w-[60%] lg:w-[35%] mt-8 pt-4 pb-8 px-8 rounded-3xl shadow-lg
                    ${darkMode ? "bg-[#2A2544]" : "bg-[#F5F3FF]"}`}
        >
          <label
            className={`py-2 block text-sm ${darkMode ? "text-[#C4B5FD]" : "text-[#4C1D95]"}`}
          >
            Currency name or ISO code
          </label>
          <input
            type="text"
            className={`mb-6 rounded-md w-full pl-2 py-2 outline-none
                            ${
                              darkMode
                                ? "bg-[#1E1B2E] text-white focus:ring-[#7C3AED]"
                                : "bg-white focus:ring-[#C4B5FD]"
                            }`}
            placeholder="Currency name or ISO code..."
            onChange={() => {}}
          />
          <ul>
            {currencies.map((c) => (
              <li
                key={c.isoCode}
                className={`flex justify-between border-b-2 py-2
                ${darkMode ? "border-[#7C3AED] text-[#DDD6FE]" : "border-[#C4B5FD]"}`}
              >
                <p>{c.isoCode}</p>
                <p>{c.name}</p>
              </li>
            ))}
          </ul>
        </div>
      </div>
    </div>
  );
}

export default App;
